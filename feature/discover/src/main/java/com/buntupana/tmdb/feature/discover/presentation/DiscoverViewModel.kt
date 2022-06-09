package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    private val getPopularMoviesListUseCase: GetPopularMoviesListUseCase,
    private val getFreeToWatchMediaListUseCase: GetFreeToWatchMediaListUseCase,
    private val getTrendingMediaListUseCase: GetTrendingMediaListUseCase
) : ViewModel() {

    var state by mutableStateOf(DiscoverState())
    private var getPopularMoviesJob: Job? = null
    private var getFreeToWatchJob: Job? = null
    private var getTrendingJob: Job? = null

    val popularFilterSet by mutableStateOf(
        setOf(
            PopularFilter.Streaming,
            PopularFilter.OnTv,
            PopularFilter.ForRent,
            PopularFilter.InTheatres,
        )
    )

    var popularFilterSelected = 0

    val freeToWatchFilterSet by mutableStateOf(
        setOf(
            FreeToWatchFilter.Movies,
            FreeToWatchFilter.TvShows
        )
    )

    var freeToWatchFilterSelected = 0

    val trendingFilterSet by mutableStateOf(
        setOf(
            TrendingFilter.Today,
            TrendingFilter.ThisWeek
        )
    )

    var trendingFilterSelected = 0

    init {
        getPopularMovies(PopularType.STREAMING)
        getFreeToWatchList(FreeToWatchType.MOVIES)
        getTrendingList(TrendingType.TODAY)
    }

    fun onEvent(event: DiscoverEvent) {
        when (event) {
            is DiscoverEvent.ChangePopularType -> {
                if (event.popularType != state.popularType) {
                    getPopularMovies(event.popularType)
                }
            }
            is DiscoverEvent.ChangeFreeToWatchType -> {
                if (event.freeToWatchType != state.freeToWatchType) {
                    getFreeToWatchList(event.freeToWatchType)
                }
            }
            is DiscoverEvent.ChangeTrendingType -> {
                if (event.trendingType != state.trendingType) {
                    getTrendingList(event.trendingType)
                }
            }
        }
    }

    private fun getPopularMovies(popularType: PopularType) {

        getPopularMoviesJob?.cancel()
        getPopularMoviesJob = viewModelScope.launch {
            getPopularMoviesListUseCase(popularType) {
                loading {
                    state = state.copy(isLoading = true, popularType = popularType)
                }
                error {

                }
                success { data ->
                    state = state.copy(isLoading = false, popularMediaItemList = data)
                }
            }
        }
    }

    private fun getFreeToWatchList(freeToWatchType: FreeToWatchType) {

        getFreeToWatchJob?.cancel()
        getFreeToWatchJob = viewModelScope.launch {
            getFreeToWatchMediaListUseCase(freeToWatchType) {
                loading {
                    state = state.copy(isLoading = true, freeToWatchType = freeToWatchType)
                }
                success { data ->
                    state = state.copy(isLoading = false, freeToWatchMediaItemList = data)
                }
            }
        }
    }

    private fun getTrendingList(trendingType: TrendingType) {
        getTrendingJob?.cancel()
        getTrendingJob = viewModelScope.launch {
            getTrendingMediaListUseCase(trendingType){
                loading {
                    state = state.copy(isLoading = true, trendingType = trendingType)
                }
                success { data ->
                    state = state.copy(isLoading = false, trendingMediaItemList = data)
                }
            }
        }
    }
}