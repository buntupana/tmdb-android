package com.buntupana.tmdb.feature.lists.presentation.manage_lists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.snackbar.SnackbarController
import com.buntupana.tmdb.core.ui.snackbar.SnackbarEvent
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.core.ui.util.navArgs
import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.buntupana.tmdb.feature.lists.domain.usecase.GetListsFromMediaUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.SetListsForMediaUseCase
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber


class ManageListsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getListsFromMediaUseCase: GetListsFromMediaUseCase,
    private val setListsForMediaUseCase: SetListsForMediaUseCase
) : ViewModel() {

    private val navArgs: ManageListsRoute = savedStateHandle.navArgs()

    var state by mutableStateOf(
        ManageListsState(
            mediaType = navArgs.mediaType,
            backgroundColor = navArgs.backgroundColor,
            mediaName = navArgs.mediaName,
            posterUrl = navArgs.posterUrl,
            releaseYear = navArgs.releaseYear
        )
    )
        private set

    private val _sideEffect = Channel<ManageListsSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private var listMediaListsOri = listOf<UserListDetails>()
    private var listAllListsOri = listOf<UserListDetails>()

    private var getMediaListsJob: Job? = null

    init {
        onEvent(ManageListsEvent.GetLists)
    }

    fun onEvent(event: ManageListsEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                is ManageListsEvent.GetLists -> getListFromMedia()

                is ManageListsEvent.AddToList -> addToList(event.mediaList)

                is ManageListsEvent.DeleteFromList -> deleteFromList(event.mediaList)

                ManageListsEvent.Confirm -> setListsForMedia()
            }
        }
    }

    private fun getListFromMedia() {

        state = state.copy(isContentLoading = true, isError = false)

        getMediaListsJob?.cancel()

        getMediaListsJob = viewModelScope.launch {
            getListsFromMediaUseCase(navArgs.mediaId, navArgs.mediaType).collectLatest { result ->
                result.onError {
                    state = state.copy(isContentLoading = false, isError = true)
                }.onSuccess {
                    listMediaListsOri = it.mediaBelongsList
                    listAllListsOri = it.allListsList

                    // If there is already data in the lists, it will filter in order to no modify the current list selection
                    val (userListDetails, listAllLists) = if (
                        state.userListDetails == null || state.listAllLists == null
                    ) {
                        listMediaListsOri to it.mediaNotBelongsList
                    } else {
                        // as the items are modified, it will compared by id and not by reference
                        state.userListDetails to listAllListsOri.filterNot { listItem ->
                            state.userListDetails.orEmpty().any { listItem.id == it.id }
                        }
                    }

                    state = state.copy(
                        isContentLoading = false,
                        userListDetails = userListDetails,
                        listAllLists = listAllLists.toList()
                    )
                }
            }
        }
    }

    private fun addToList(mediaList: UserListDetails) {
        val newMediaList = state.userListDetails?.toMutableList() ?: mutableListOf()
        // Adding one item to the list item count
        newMediaList.add(index = 0, element = mediaList.copy(itemCount = mediaList.itemCount + 1))
        val newAllList = state.listAllLists?.toMutableList()
        newAllList?.remove(mediaList)
        state = state.copy(userListDetails = newMediaList, listAllLists = newAllList)
    }

    private fun deleteFromList(mediaList: UserListDetails) {
        val newMediaList = state.userListDetails?.toMutableList() ?: mutableListOf()
        newMediaList.remove(mediaList)

        val newAllList = listAllListsOri.filterNot {
            newMediaList.map { listItem -> listItem.id }.contains(it.id)
        }.map {
            // Removing one item from the list item count
            if (mediaList.id == it.id) {
                it.copy(itemCount = mediaList.itemCount - 1)
            } else {
                it
            }
        }

        state = state.copy(userListDetails = newMediaList, listAllLists = newAllList)
    }

    private suspend fun setListsForMedia() {
        state = state.copy(isConfirmLoading = true)

        setListsForMediaUseCase(
            mediaId = navArgs.mediaId,
            mediaType = navArgs.mediaType,
            originalList = listMediaListsOri,
            newList = state.userListDetails?.reversed() ?: listOf()
        ).onError {
            state = state.copy(isConfirmLoading = false)

            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = UiText.StringResource(R.string.lists_set_lists_error)
                )
            )
        }.onSuccess {
            _sideEffect.send(ManageListsSideEffect.SetListsSuccess)
        }
    }
}