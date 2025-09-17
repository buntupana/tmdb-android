package com.buntupana.tmdb.feature.lists.presentation.delete_item_list

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
import com.buntupana.tmdb.feature.lists.domain.usecase.DeleteItemListUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.SetMediaFavoriteUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.SetMediaWatchListUseCase
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber


class DeleteItemListViewModel(
    savedStateHandle: SavedStateHandle,
    private val deleteItemListUseCase: DeleteItemListUseCase,
    private val setMediaFavoriteUseCase: SetMediaFavoriteUseCase,
    private val setMediaWatchListUseCase: SetMediaWatchListUseCase
) : ViewModel() {

    private val navArgs = savedStateHandle.navArgs<DeleteItemListNav>(typeMap = DeleteItemListNav.typeMap)

    var state by mutableStateOf(
        DeleteItemListState(
            mediaName = navArgs.mediaName,
            mediaId = navArgs.mediaId,
            mediaType = navArgs.mediaType,
            listType = navArgs.listType
        )
    )
        private set
    private var _sideEffect = Channel<DeleteItemListSideEffect>()

    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: DeleteItemListEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                DeleteItemListEvent.ConfirmDelete -> {
                    when (navArgs.listType) {
                        is ListType.CustomList -> deleteMediaFromList(navArgs.listType.listId)
                        ListType.Favorites -> deleteMediaFromFavorites()
                        ListType.Watchlist -> deleteMediaFromWatchlist()
                    }
                }
            }
        }
    }

    private suspend fun deleteMediaFromList(listId: Long) {
        state = state.copy(isLoading = true)

        deleteItemListUseCase(
            listId = listId,
            mediaId = navArgs.mediaId,
            mediaType = navArgs.mediaType
        ).onError {
            state = state.copy(isLoading = false)
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = UiText.StringResource(
                        R.string.message_error_delete_item_list,
                        navArgs.mediaName
                    )
                )
            )
        }.onSuccess {
            _sideEffect.send(DeleteItemListSideEffect.DeleteSuccess)
        }
    }

    private suspend fun deleteMediaFromFavorites() {
        state = state.copy(isLoading = true)

        setMediaFavoriteUseCase(
            mediaId = navArgs.mediaId,
            mediaType = navArgs.mediaType,
            isFavorite = false
        ).onError {
            state = state.copy(isLoading = false)
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = UiText.StringResource(
                        R.string.message_error_delete_item_list,
                        navArgs.mediaName
                    )
                )
            )
        }.onSuccess {
            _sideEffect.send(DeleteItemListSideEffect.DeleteSuccess)
        }
    }

    private suspend fun deleteMediaFromWatchlist() {
        state = state.copy(isLoading = true)

        setMediaWatchListUseCase(
            mediaId = navArgs.mediaId,
            mediaType = navArgs.mediaType,
            watchlist = false
        ).onError {
            state = state.copy(isLoading = false)
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = UiText.StringResource(
                        R.string.message_error_delete_item_list,
                        navArgs.mediaName
                    )
                )
            )
        }.onSuccess {
            _sideEffect.send(DeleteItemListSideEffect.DeleteSuccess)
        }
    }
}