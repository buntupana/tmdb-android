package com.buntupana.tmdb.feature.lists.presentation.list_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.snackbar.SnackbarController
import com.buntupana.tmdb.core.ui.snackbar.SnackbarEvent
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.core.ui.util.navArgs
import com.buntupana.tmdb.feature.lists.domain.usecase.GetListDetailsUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetListItemsPagingUseCase
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getListDetailsUseCase: GetListDetailsUseCase,
    private val getListItemsPagingUseCase: GetListItemsPagingUseCase
) : ViewModel() {

    private val navArgs = savedStateHandle.navArgs<ListDetailNav>()

    var state by mutableStateOf(
        ListDetailState(
            listId = navArgs.listId,
            listName = navArgs.listName,
            description = navArgs.description,
            backdropUrl = navArgs.backdropUrl
        )
    )
        private set

    private var _sideEffect = Channel<ListDetailSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: ListDetailEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                ListDetailEvent.GetDetails -> getListDetails()

                is ListDetailEvent.CancelDeleteItemList -> {
                    cancelDeleteItemList(event.itemId)
                }

                is ListDetailEvent.SuccessDeleteItemList -> deleteItemList(
                    itemId = event.itemId
                )
            }
        }
    }

    private suspend fun getListDetails() {

        state = state.copy(isLoading = state.mediaItemList == null, isError = false)

        getListDetailsUseCase(navArgs.listId)
            .onError {
                if (state.itemTotalCount == null) {
                    state = state.copy(isLoading = false, isError = true)
                } else {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            UiText.StringResource(R.string.message_refresh_content_error)
                        )
                    )
                }
            }.onSuccess { listDetails ->
                state = state.copy(
                    isLoading = false,
                    listName = listDetails.name,
                    description = listDetails.description,
                    backdropUrl = listDetails.backdropUrl,
                    isPublic = listDetails.isPublic,
                    itemTotalCount = listDetails.itemCount
                )
                if (state.mediaItemList == null) {
                    getListItems()
                } else {
                    _sideEffect.send(ListDetailSideEffect.RefreshMediaItemList)
                }
            }
    }

    private fun getListItems() {

        getListItemsPagingUseCase(navArgs.listId).let {
            state = state.copy(
                mediaItemList = it.map { pagingData ->
                    pagingData.map { mediaItem ->
                        ItemListViewEntity(
                            id = "${mediaItem.id}_${mediaItem.mediaType}",
                            mediaItem = mediaItem
                        )
                    }
                }.cachedIn(viewModelScope)
            )
        }
    }

    private fun deleteItemList(itemId: String) {
        val pagingList = state.mediaItemList?.map { pagingData ->
            pagingData.filter { item ->
                (itemId == item.id).not()
            }
        }
        state = state.copy(
            mediaItemList = pagingList?.cachedIn(viewModelScope),
            itemTotalCount = (state.itemTotalCount ?: 0) - 1
        )
    }

    private fun cancelDeleteItemList(itemId: String) {
        val pagingList = state.mediaItemList?.map { pagingData ->
            pagingData.map { item ->
                if (itemId == item.id) {
                    item.isDeleteRevealed = false
                }
                item
            }
        }
        state = state.copy(mediaItemList = pagingList?.cachedIn(viewModelScope))
    }
}