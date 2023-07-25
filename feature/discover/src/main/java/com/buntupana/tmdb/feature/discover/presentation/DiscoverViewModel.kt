package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType
import com.buntupana.tmdb.feature.discover.domain.usecase.GetFreeToWatchMediaListUseCase
import com.buntupana.tmdb.feature.discover.domain.usecase.GetPopularMoviesListUseCase
import com.buntupana.tmdb.feature.discover.domain.usecase.GetTrendingMediaListUseCase
import com.buntupana.tmdb.feature.discover.presentation.filter_type.FreeToWatchFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.PopularFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.TrendingFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPopularMoviesListUseCase: GetPopularMoviesListUseCase,
    private val getFreeToWatchMediaListUseCase: GetFreeToWatchMediaListUseCase,
    private val getTrendingMediaListUseCase: GetTrendingMediaListUseCase
) : ViewModel() {

    var state by mutableStateOf(DiscoverState())
    private var getPopularMoviesJob: Job? = null
    private var getFreeToWatchJob: Job? = null
    private var getTrendingJob: Job? = null

    init {
        getPopularMovies(state.popularFilterSelected)
        getFreeToWatchList(state.freeToWatchFilterSelected)
        getTrendingList(state.trendingFilterSelected)
    }

    fun onEvent(event: DiscoverEvent) {
        when (event) {
            is DiscoverEvent.ChangePopularType -> {
                if (event.popularType != state.popularFilterSelected) {
                    getPopularMovies(event.popularType)
                }
            }

            is DiscoverEvent.ChangeFreeToWatchType -> {
                if (event.freeToWatchFilter != state.freeToWatchFilterSelected) {
                    getFreeToWatchList(event.freeToWatchFilter)
                }
            }

            is DiscoverEvent.ChangeTrendingType -> {
                if (event.trendingFilter != state.trendingFilterSelected) {
                    getTrendingList(event.trendingFilter)
                }
            }
        }
    }

    private fun getPopularMovies(popularFilter: PopularFilter) {

        val popularType = when (popularFilter) {
            PopularFilter.ForRent -> PopularType.FOR_RENT
            PopularFilter.InTheatres -> PopularType.IN_THEATRES
            PopularFilter.OnTv -> PopularType.ON_TV
            PopularFilter.Streaming -> PopularType.STREAMING
        }

        getPopularMoviesJob?.cancel()
        getPopularMoviesJob = viewModelScope.launch {
            getPopularMoviesListUseCase(popularType) {
                loading {
                    state = state.copy(isLoading = true, popularFilterSelected = popularFilter)
                }
                error {

                }
                success { data ->
                    state = state.copy(isLoading = false, popularMediaItemList = data)
                }
            }
        }
    }

    private fun getFreeToWatchList(freeToWatchFilter: FreeToWatchFilter) {

        val freeToWatchType = when (freeToWatchFilter) {
            FreeToWatchFilter.Movies -> FreeToWatchType.MOVIES
            FreeToWatchFilter.TvShows -> FreeToWatchType.TV_SHOWS
        }

        getFreeToWatchJob?.cancel()
        getFreeToWatchJob = viewModelScope.launch {
            getFreeToWatchMediaListUseCase(freeToWatchType) {
                loading {
                    state =
                        state.copy(isLoading = true, freeToWatchFilterSelected = freeToWatchFilter)
                }
                success { data ->
                    state = state.copy(isLoading = false, freeToWatchMediaItemList = data)
                }
            }
        }
    }

    private fun getTrendingList(trendingFilter: TrendingFilter) {

        val trendingType = when (trendingFilter) {
            TrendingFilter.ThisWeek -> TrendingType.THIS_WEEK
            TrendingFilter.Today -> TrendingType.TODAY
        }

        getTrendingJob?.cancel()
        getTrendingJob = viewModelScope.launch {
            getTrendingMediaListUseCase(trendingType) {
                loading {
                    state = state.copy(isLoading = true, trendingFilterSelected = trendingFilter)
                }
                success { data ->
                    state = state.copy(isLoading = false, trendingMediaItemList = data)
                }
            }
        }
    }
}