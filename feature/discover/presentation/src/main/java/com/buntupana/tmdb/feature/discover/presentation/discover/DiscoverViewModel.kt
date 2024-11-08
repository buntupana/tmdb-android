package com.buntupana.tmdb.feature.discover.presentation.discover

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.presentation.util.LOADING_DELAY
import com.buntupana.tmdb.feature.discover.presentation.filter_type.FreeToWatchFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.PopularFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.TrendingFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getPopularMoviesListUseCase: com.buntupana.tmdb.feature.discover.domain.usecase.GetPopularMoviesListUseCase,
    private val getFreeToWatchMediaListUseCase: com.buntupana.tmdb.feature.discover.domain.usecase.GetFreeToWatchMediaListUseCase,
    private val getTrendingMediaListUseCase: com.buntupana.tmdb.feature.discover.domain.usecase.GetTrendingMediaListUseCase
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
                getPopularMovies(event.popularType)
            }

            is DiscoverEvent.ChangeFreeToWatchType -> {
                getFreeToWatchList(event.freeToWatchFilter)
            }

            is DiscoverEvent.ChangeTrendingType -> {
                getTrendingList(event.trendingFilter)
            }
        }
    }

    private fun getTrendingList(trendingFilter: TrendingFilter) {

        if (trendingFilter == state.trendingFilterSelected && state.trendingMediaItemList.isNotEmpty()) {
            return
        }

        val trendingType = when (trendingFilter) {
            TrendingFilter.ThisWeek -> com.buntupana.tmdb.feature.discover.domain.entity.TrendingType.THIS_WEEK
            TrendingFilter.Today -> com.buntupana.tmdb.feature.discover.domain.entity.TrendingType.TODAY
        }

        getTrendingJob?.cancel()
        getTrendingJob = viewModelScope.launch {
            getTrendingMediaListUseCase(trendingType) {
                loading {
                    state = state.copy(
                        isLoading = true,
                        isTrendingMediaLoadingError = false,
                        trendingFilterSelected = trendingFilter,
                        trendingMediaItemList = emptyList()
                    )
                }
                error {
                    state = state.copy(
                        isLoading = false,
                        isTrendingMediaLoadingError = true
                    )
                }
                success { data ->
                    // Fake delay to show loading
                    delay(LOADING_DELAY)
                    state = state.copy(
                        isLoading = false,
                        isTrendingMediaLoadingError = false,
                        trendingMediaItemList = data
                    )
                }
            }
        }
    }

    private fun getPopularMovies(popularFilter: PopularFilter) {

        if (popularFilter == state.popularFilterSelected && state.popularMediaItemList.isNotEmpty()) {
            return
        }

        val popularType = when (popularFilter) {
            PopularFilter.ForRent -> com.buntupana.tmdb.feature.discover.domain.entity.PopularType.FOR_RENT
            PopularFilter.InTheatres -> com.buntupana.tmdb.feature.discover.domain.entity.PopularType.IN_THEATRES
            PopularFilter.OnTv -> com.buntupana.tmdb.feature.discover.domain.entity.PopularType.ON_TV
            PopularFilter.Streaming -> com.buntupana.tmdb.feature.discover.domain.entity.PopularType.STREAMING
        }

        getPopularMoviesJob?.cancel()
        getPopularMoviesJob = viewModelScope.launch {
            getPopularMoviesListUseCase(popularType) {
                loading {
                    state = state.copy(
                        isLoading = true,
                        isPopularMediaLoadingError = false,
                        popularFilterSelected = popularFilter,
                        popularMediaItemList = emptyList()
                    )
                }
                error {
                    state = state.copy(
                        isLoading = false,
                        isPopularMediaLoadingError = true,
                    )
                }
                success { data ->
                    // Fake delay to show loading
                    delay(LOADING_DELAY)
                    state = state.copy(
                        isLoading = false,
                        isPopularMediaLoadingError = false,
                        popularMediaItemList = data
                    )
                }
            }
        }
    }

    private fun getFreeToWatchList(freeToWatchFilter: FreeToWatchFilter) {

        if (freeToWatchFilter == state.freeToWatchFilterSelected && state.freeToWatchMediaItemList.isNotEmpty()) {
            return
        }

        val freeToWatchType = when (freeToWatchFilter) {
            FreeToWatchFilter.Movies -> com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType.MOVIES
            FreeToWatchFilter.TvShows -> com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType.TV_SHOWS
        }

        getFreeToWatchJob?.cancel()
        getFreeToWatchJob = viewModelScope.launch {
            getFreeToWatchMediaListUseCase(freeToWatchType) {
                loading {
                    state =
                        state.copy(
                            isLoading = true,
                            isFreeToWatchMediaLoadingError = false,
                            freeToWatchFilterSelected = freeToWatchFilter,
                            freeToWatchMediaItemList = emptyList()
                        )
                }
                error {
                    state = state.copy(isLoading = false, isFreeToWatchMediaLoadingError = false)
                }
                success { data ->// Fake delay to show loading
                    delay(LOADING_DELAY)
                    state = state.copy(
                        isLoading = false,
                        isFreeToWatchMediaLoadingError = false,
                        freeToWatchMediaItemList = data
                    )
                }
            }
        }
    }
}