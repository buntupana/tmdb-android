package com.buntupana.tmdb.feature.account.presentation.watchlist_favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.util.navArgs
import com.buntupana.tmdb.feature.account.domain.usecase.GetFavoriteTotalCountUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetMediaItemTotalCountResult
import com.buntupana.tmdb.feature.account.domain.usecase.GetMovieFavoritesPagingUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetMovieWatchlistPagingUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetTvShowFavoritesPagingUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetTvShowWatchlistPagingUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetWatchlistTotalCountUseCase
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import com.panabuntu.tmdb.core.common.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WatchlistFavoritesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieWatchlistPagingUseCase: GetMovieWatchlistPagingUseCase,
    private val getTvShowWatchlistPagingUseCase: GetTvShowWatchlistPagingUseCase,
    private val getWatchlistTotalCountUseCase: GetWatchlistTotalCountUseCase,
    private val getMovieFavoritesPagingUseCase: GetMovieFavoritesPagingUseCase,
    private val getTvShowFavoritesPagingUseCase: GetTvShowFavoritesPagingUseCase,
    private val getFavoriteTotalCountUseCase: GetFavoriteTotalCountUseCase,
) : ViewModel() {

    private val args = savedStateHandle.navArgs<WatchListFavoritesNav>()

    var state by mutableStateOf(
        WatchlistFavoritesState(
            screenType = args.screenType,
            defaultPage = MediaFilter.entries.indexOf(args.mediaFilterSelected)
        )
    )
        private set

    private var _sideEffect = Channel<WatchlistFavoritesSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: WatchlistFavoritesEvent) {
        Timber.d("onEvent() called with: event = $event")
        viewModelScope.launch {
            when (event) {

                WatchlistFavoritesEvent.ChangeOrder -> changeOrder()

                WatchlistFavoritesEvent.GetMediaItemList -> getTotalItemCount()
            }
        }
    }

    private suspend fun getTotalItemCount() {

        val getItemsUseCase: suspend () -> Result<GetMediaItemTotalCountResult, NetworkError> =
            when (state.screenType) {
                ScreenType.WATCHLIST -> {
                    { getWatchlistTotalCountUseCase() }
                }

                ScreenType.FAVORITES -> {
                    { getFavoriteTotalCountUseCase() }
                }
            }

        state = state.copy(
            isError = false,
            isLoading = state.movieItemsTotalCount == null || state.tvShowItemsTotalCount == null
        )

        getItemsUseCase()
            .onError {
                state = state.copy(isLoading = false, isError = true)
            }
            .onSuccess {
                state = state.copy(
                    isLoading = false,
                    movieItemsTotalCount = it.movieTotalCount,
                    tvShowItemsTotalCount = it.tvShowTotalCount
                )
            }

        if (state.movieItems == null || state.tvShowItems == null) {
            getMediaItems(MediaFilter.MOVIES)
            getMediaItems(MediaFilter.TV_SHOWS)
        } else {
            _sideEffect.send(WatchlistFavoritesSideEffect.RefreshMediaItemList)
        }
    }

    private suspend fun getMediaItems(mediaFilter: MediaFilter) {

        when {
            mediaFilter == MediaFilter.MOVIES && state.screenType == ScreenType.WATCHLIST -> {
                getMovieWatchlistPagingUseCase(order = state.order).let {
                    state = state.copy(movieItems = it.cachedIn(viewModelScope))
                }
            }

            mediaFilter == MediaFilter.TV_SHOWS && state.screenType == ScreenType.WATCHLIST -> {
                getTvShowWatchlistPagingUseCase(order = state.order).let {
                    state = state.copy(tvShowItems = it.cachedIn(viewModelScope))
                }
            }

            mediaFilter == MediaFilter.MOVIES && state.screenType == ScreenType.FAVORITES -> {
                getMovieFavoritesPagingUseCase(order = state.order).let {
                    state = state.copy(movieItems = it.cachedIn(viewModelScope))
                }
            }

            mediaFilter == MediaFilter.TV_SHOWS && state.screenType == ScreenType.FAVORITES -> {
                getTvShowFavoritesPagingUseCase(order = state.order).let {
                    state = state.copy(tvShowItems = it.cachedIn(viewModelScope))
                }
            }
        }
    }

    private suspend fun changeOrder() {
        val order = when (state.order) {
            Order.ASC -> Order.DESC
            Order.DESC -> Order.ASC
        }
        state = state.copy(order = order, movieItems = null, tvShowItems = null)
        getTotalItemCount()
    }
}