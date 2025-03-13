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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import com.buntupana.tmdb.core.ui.R as RCore

@HiltViewModel
class ManageListsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getListsFromMediaPagingUseCase: GetListsFromMediaUseCase,
    private val setListsForMediaUseCase: SetListsForMediaUseCase
) : ViewModel() {

    private val navArgs: ManageListsNav = savedStateHandle.navArgs()

    var state by mutableStateOf(
        ManageListsState(mediaType = navArgs.mediaType)
    )
        private set

    private val _sideEffect = Channel<ManageListsSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private var listMediaListsOri = listOf<UserListDetails>()
    private var listAllListsOri = listOf<UserListDetails>()

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

    private suspend fun getListFromMedia() {

        state = state.copy(isLoading = true)

        getListsFromMediaPagingUseCase(navArgs.mediaId, navArgs.mediaType)
            .onError {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = UiText.StringResource(RCore.string.message_loading_content_error)
                    )
                )
                _sideEffect.send(ManageListsSideEffect.Dismiss)
            }
            .onSuccess { result ->
                listMediaListsOri = result.mediaBelongsList
                listAllListsOri = result.allListsList

                state = state.copy(
                    isLoading = false,
                    userListDetails = listMediaListsOri,
                    listAllLists = result.mediaNotBelongsList
                )
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
        state = state.copy(isLoading = true)

        setListsForMediaUseCase(
            mediaId = navArgs.mediaId,
            mediaType = navArgs.mediaType,
            originalList = listMediaListsOri,
            newList = state.userListDetails?.reversed() ?: listOf()
        ).onError {
            state = state.copy(isLoading = false)

            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = UiText.StringResource(R.string.message_set_lists_error)
                )
            )
        }.onSuccess {
            _sideEffect.send(ManageListsSideEffect.SetListsSuccess)
        }
    }
}