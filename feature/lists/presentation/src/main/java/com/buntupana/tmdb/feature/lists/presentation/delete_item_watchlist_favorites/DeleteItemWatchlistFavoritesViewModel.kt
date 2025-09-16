package com.buntupana.tmdb.feature.lists.presentation.delete_item_watchlist_favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.snackbar.SnackbarController
import com.buntupana.tmdb.core.ui.snackbar.SnackbarEvent
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.feature.lists.domain.usecase.SetMediaFavoriteUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.SetMediaWatchListUseCase
import com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.ScreenType
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber


class DeleteItemWatchlistFavoritesViewModel(
    private val setMediaFavoriteUseCase: SetMediaFavoriteUseCase,
    private val setMediaWatchListUseCase: SetMediaWatchListUseCase
) : ViewModel() {

    var state by mutableStateOf(DeleteItemWatchlistFavoritesState())
        private set

    private var _sideEffect = Channel<DeleteItemWatchlistFavoritesSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private lateinit var navArgs: DeleteItemWatchlistFavoritesNav

    fun onEvent(event: DeleteItemWatchlistFavoritesEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {

                DeleteItemWatchlistFavoritesEvent.ConfirmDelete -> {
                    when (navArgs.screenType) {
                        ScreenType.WATCHLIST -> deleteMediaFromWatchlist()
                        ScreenType.FAVORITES -> deleteMediaFromFavorites()
                    }
                }

                is DeleteItemWatchlistFavoritesEvent.Init -> {
                    navArgs = event.deleteItemWatchlistFavoritesNav
                    state = state.copy(
                        screenType = navArgs.screenType,
                        mediaName = navArgs.mediaName,
                        isLoading = false
                    )
                }
            }
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
            _sideEffect.send(DeleteItemWatchlistFavoritesSideEffect.DeleteSuccess)
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
            _sideEffect.send(DeleteItemWatchlistFavoritesSideEffect.DeleteSuccess)
        }
    }
}