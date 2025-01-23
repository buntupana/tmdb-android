package com.buntupana.tmdb.feature.discover.presentation.discover

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.util.LOADING_DELAY
import com.buntupana.tmdb.feature.discover.domain.usecase.GetFreeToWatchMediaListUseCase
import com.buntupana.tmdb.feature.discover.domain.usecase.GetPopularMoviesListUseCase
import com.buntupana.tmdb.feature.discover.domain.usecase.GetTrendingMediaListUseCase
import com.buntupana.tmdb.feature.discover.presentation.filter_type.FreeToWatchFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.PopularFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.TrendingFilter
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getPopularMoviesListUseCase: GetPopularMoviesListUseCase,
    private val getFreeToWatchMediaListUseCase: GetFreeToWatchMediaListUseCase,
    private val getTrendingMediaListUseCase: GetTrendingMediaListUseCase
) : ViewModel() {

    var state by mutableStateOf(DiscoverState())
        private set

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
            state = state.copy(
                isLoading = true,
                isTrendingMediaLoadingError = false,
                trendingFilterSelected = trendingFilter,
                trendingMediaItemList = emptyList()
            )
            getTrendingMediaListUseCase(trendingType)
                .onError {
                    state = state.copy(
                        isLoading = false,
                        isTrendingMediaLoadingError = true
                    )
                }
                .onSuccess { mediaItemList ->
                    // Fake delay to show loading
                    delay(LOADING_DELAY)
                    state = state.copy(
                        isLoading = false,
                        isTrendingMediaLoadingError = false,
                        trendingMediaItemList = mediaItemList
                    )
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
            state = state.copy(
                isLoading = true,
                isPopularMediaLoadingError = false,
                popularFilterSelected = popularFilter,
                popularMediaItemList = emptyList()
            )
            getPopularMoviesListUseCase(popularType)
                .onError {
                    state = state.copy(
                        isLoading = false,
                        isPopularMediaLoadingError = true,
                    )
                }
                .onSuccess { mediaItemList ->
                    // Fake delay to show loading
                    delay(LOADING_DELAY)
                    state = state.copy(
                        isLoading = false,
                        isPopularMediaLoadingError = false,
                        popularMediaItemList = mediaItemList
                    )
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
            state =
                state.copy(
                    isLoading = true,
                    isFreeToWatchMediaLoadingError = false,
                    freeToWatchFilterSelected = freeToWatchFilter,
                    freeToWatchMediaItemList = emptyList()
                )

            getFreeToWatchMediaListUseCase(freeToWatchType)
                .onError {
                    state = state.copy(isLoading = false, isFreeToWatchMediaLoadingError = false)
                }
                .onSuccess { mediaItemList ->
                    delay(LOADING_DELAY)
                    state = state.copy(
                        isLoading = false,
                        isFreeToWatchMediaLoadingError = false,
                        freeToWatchMediaItemList = mediaItemList
                    )
                }
        }
    }
}