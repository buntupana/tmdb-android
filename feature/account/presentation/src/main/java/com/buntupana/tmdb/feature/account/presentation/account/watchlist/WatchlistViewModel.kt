package com.buntupana.tmdb.feature.account.presentation.account.watchlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.feature.account.domain.usecase.GetMovieWatchlistPagingUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetTvShowWatchlistPagingUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.SetMediaWatchListUseCase
import com.panabuntu.tmdb.core.common.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMovieWatchlistPagingUseCase: GetMovieWatchlistPagingUseCase,
    private val getTvShowWatchlistPagingUseCase: GetTvShowWatchlistPagingUseCase,
    private val setMediaWatchListUseCase: SetMediaWatchListUseCase,
) : ViewModel() {

    var state by mutableStateOf(WatchlistState())
        private set

    private var _sideEffect = Channel<WatchlistSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: WatchlistEvent) {
        Timber.d("onEvent() called with: event = $event")
        viewModelScope.launch {
            when (event) {
                is WatchlistEvent.GetWatchlist -> getWatchlist(event.mediaFilter)

                WatchlistEvent.ChangeOrder -> changeOrder()

                WatchlistEvent.GetWatchLists -> getWatchlist()
            }
        }
    }

    private suspend fun getWatchlist() {
        if (state.movieItems == null || state.tvShowItems == null) {
            onEvent(WatchlistEvent.GetWatchlist(MediaFilter.Movies))
            onEvent(WatchlistEvent.GetWatchlist(MediaFilter.TvShows))
        } else {
            _sideEffect.send(WatchlistSideEffect.GetWatchlist)
        }
    }

    private suspend fun getWatchlist(mediaType: MediaFilter) {

        when (mediaType) {
            MediaFilter.Movies -> {
                getMovieWatchlistPagingUseCase(order = state.order).let {
                    state = state.copy(movieItems = it.cachedIn(viewModelScope))
                }
            }

            MediaFilter.TvShows -> {
                getTvShowWatchlistPagingUseCase(order = state.order).let {
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
        getWatchlist()
    }
}