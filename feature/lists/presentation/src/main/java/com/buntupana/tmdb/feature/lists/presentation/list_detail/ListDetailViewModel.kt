package com.buntupana.tmdb.feature.lists.presentation.list_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber


class ListDetailViewModel(
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

    init {
        onEvent(ListDetailEvent.GetDetails)
    }

    fun onEvent(event: ListDetailEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                ListDetailEvent.GetDetails -> getListDetails()
            }
        }
    }

    private suspend fun getListDetails() {

        state = state.copy(isLoading = state.mediaItemList == null, isError = false)

        getListDetailsUseCase(navArgs.listId).collectLatest {
            it.onError {
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

                if (listDetails == null) {
                    Timber.d("getListDetails: null")
                    _sideEffect.send(ListDetailSideEffect.NavigateBack)
                    return@onSuccess
                }

                state = state.copy(
                    isLoading = false,
                    listName = listDetails.name,
                    description = listDetails.description,
                    backdropUrl = listDetails.backdropUrl,
                    isPublic = listDetails.isPublic,
                    itemTotalCount = listDetails.itemCount,
                    shareLink = if (listDetails.isPublic) listDetails.shareLink else null
                )
                if (state.mediaItemList == null) {
                    getListItems()
                }
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
}