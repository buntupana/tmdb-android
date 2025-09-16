package com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.util.MediaItemRevealedViewEntity
import com.buntupana.tmdb.core.ui.util.navArgs
import com.buntupana.tmdb.feature.lists.domain.usecase.GetFavoriteTotalCountUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetMediaItemTotalCountResult
import com.buntupana.tmdb.feature.lists.domain.usecase.GetMovieFavoritesPagingUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetMovieWatchlistPagingUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetTvShowFavoritesPagingUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetTvShowWatchlistPagingUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetWatchlistTotalCountUseCase
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import com.panabuntu.tmdb.core.common.model.Order
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber


class WatchlistFavoritesViewModel(
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

    private var getWatchListData: Job? = null

    init {
        onEvent(WatchlistFavoritesEvent.GetMediaItemList)
    }

    fun onEvent(event: WatchlistFavoritesEvent) {
        Timber.d("onEvent() called with: event = $event")
        viewModelScope.launch {
            when (event) {

                WatchlistFavoritesEvent.ChangeOrder -> changeOrder()

                WatchlistFavoritesEvent.GetMediaItemList -> getWatchlistData()
            }
        }
    }

    private fun getWatchlistData() {

        getWatchListData?.cancel()

        getWatchListData = viewModelScope.launch {

            val getItemsUseCase: suspend () -> Flow<Result<GetMediaItemTotalCountResult, NetworkError>> =
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

            getItemsUseCase().collectLatest { result ->
                result.onError {
                    state = state.copy(isLoading = false, isError = true)
                }.onSuccess {
                    state = state.copy(
                        isLoading = false,
                        movieItemsTotalCount = it.movieTotalCount,
                        tvShowItemsTotalCount = it.tvShowTotalCount
                    )
                    if (state.movieItems == null || state.tvShowItems == null) {
                        getMediaItems(MediaFilter.MOVIES)
                        getMediaItems(MediaFilter.TV_SHOWS)
                    }
                }
            }
        }
    }

    private suspend fun getMediaItems(mediaFilter: MediaFilter) {
        Timber.d("getMediaItems() called with: mediaFilter = [$mediaFilter]")
        when {
            mediaFilter == MediaFilter.MOVIES && state.screenType == ScreenType.WATCHLIST -> {
                state = state.copy(movieItems = null, tvShowItems = null)
                getMovieWatchlistPagingUseCase(order = state.order).let {
                    state = state.copy(
                        movieItems = it.map { pagingData ->
                            pagingData.map { mediaItem ->
                                MediaItemRevealedViewEntity(
                                    id = mediaItem.id.toString(),
                                    mediaItem = mediaItem
                                )
                            }
                        }.cachedIn(viewModelScope)
                    )
                }
            }

            mediaFilter == MediaFilter.TV_SHOWS && state.screenType == ScreenType.WATCHLIST -> {
                state = state.copy(tvShowItems = null)
                getTvShowWatchlistPagingUseCase(order = state.order).let {
                    state = state.copy(
                        tvShowItems = it.map { pagingData ->
                            pagingData.map { mediaItem ->
                                MediaItemRevealedViewEntity(
                                    id = mediaItem.id.toString(),
                                    mediaItem = mediaItem
                                )
                            }
                        }.cachedIn(viewModelScope)
                    )
                }
            }

            mediaFilter == MediaFilter.MOVIES && state.screenType == ScreenType.FAVORITES -> {
                state = state.copy(movieItems = null)
                getMovieFavoritesPagingUseCase(order = state.order).let {
                    state = state.copy(
                        movieItems = it.map { pagingData ->
                            pagingData.map { mediaItem ->
                                MediaItemRevealedViewEntity(
                                    id = mediaItem.id.toString(),
                                    mediaItem = mediaItem
                                )
                            }
                        }.cachedIn(viewModelScope)
                    )
                }
            }

            mediaFilter == MediaFilter.TV_SHOWS && state.screenType == ScreenType.FAVORITES -> {
                state = state.copy(tvShowItems = null)
                getTvShowFavoritesPagingUseCase(order = state.order).let {
                    state = state.copy(
                        tvShowItems = it.map { pagingData ->
                            pagingData.map { mediaItem ->
                                MediaItemRevealedViewEntity(
                                    id = mediaItem.id.toString(),
                                    mediaItem = mediaItem
                                )
                            }
                        }.cachedIn(viewModelScope)
                    )
                }
            }
        }
    }

    private fun changeOrder() {
        val order = when (state.order) {
            Order.ASC -> Order.DESC
            Order.DESC -> Order.ASC
        }
        state = state.copy(order = order, movieItems = null, tvShowItems = null)
        getWatchlistData()
    }
}