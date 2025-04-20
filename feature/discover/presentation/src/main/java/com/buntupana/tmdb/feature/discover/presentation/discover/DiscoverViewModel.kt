package com.buntupana.tmdb.feature.discover.presentation.discover

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.util.LOADING_DELAY
import com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import com.buntupana.tmdb.feature.discover.domain.usecase.GetFreeToWatchMediaListUseCase
import com.buntupana.tmdb.feature.discover.domain.usecase.GetPopularMoviesListUseCase
import com.buntupana.tmdb.feature.discover.domain.usecase.GetTrendingMediaListUseCase
import com.buntupana.tmdb.feature.discover.presentation.filter_type.PopularFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.TrendingFilter
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import com.panabuntu.tmdb.core.common.manager.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getPopularMoviesListUseCase: GetPopularMoviesListUseCase,
    private val getFreeToWatchMediaListUseCase: GetFreeToWatchMediaListUseCase,
    private val getTrendingMediaListUseCase: GetTrendingMediaListUseCase,
    sessionManager: SessionManager
) : ViewModel() {

    var state by mutableStateOf(DiscoverState())
        private set

    private var countryCode: String? = null

    private var getPopularMoviesJob: Job? = null
    private var getFreeToWatchJob: Job? = null
    private var getTrendingJob: Job? = null

    init {
        viewModelScope.launch {
            sessionManager.session.collectLatest { session ->
                if (session.countryCode != countryCode) {
                    countryCode = session.countryCode
                    state = state.copy(
                        trendingMediaItemList = null,
                        popularMediaItemList = null,
                        freeToWatchMediaItemList = null
                    )
                    getPopularMovies(state.popularFilterSelected)
                    getFreeToWatchList(state.freeToWatchFilterSelected)
                    getTrendingList(state.trendingFilterSelected)
                }
            }
        }
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

        if (trendingFilter == state.trendingFilterSelected && state.trendingMediaItemList?.isNotEmpty() == true) {
            return
        }

        val trendingType = when (trendingFilter) {
            TrendingFilter.THIS_WEEK -> com.buntupana.tmdb.feature.discover.domain.entity.TrendingType.THIS_WEEK
            TrendingFilter.TODAY -> com.buntupana.tmdb.feature.discover.domain.entity.TrendingType.TODAY
        }

        getTrendingJob?.cancel()
        getTrendingJob = viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                isTrendingMediaLoadingError = false,
                trendingFilterSelected = trendingFilter,
                trendingMediaItemList = null
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

        if (popularFilter == state.popularFilterSelected && state.popularMediaItemList?.isNotEmpty() == true) {
            return
        }

        val popularType = when (popularFilter) {
            PopularFilter.FOR_RENT -> PopularType.FOR_RENT
            PopularFilter.IN_THEATRES -> PopularType.IN_THEATRES
            PopularFilter.ON_TV -> PopularType.ON_TV
            PopularFilter.STREAMING -> PopularType.STREAMING
        }

        getPopularMoviesJob?.cancel()
        getPopularMoviesJob = viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                isPopularMediaLoadingError = false,
                popularFilterSelected = popularFilter,
                popularMediaItemList = null
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

    private fun getFreeToWatchList(freeToWatchFilter: MediaFilter) {

        if (freeToWatchFilter == state.freeToWatchFilterSelected && state.freeToWatchMediaItemList?.isNotEmpty() == true) {
            return
        }

        val freeToWatchType = when (freeToWatchFilter) {
            MediaFilter.MOVIES -> FreeToWatchType.MOVIES
            MediaFilter.TV_SHOWS -> FreeToWatchType.TV_SHOWS
        }

        getFreeToWatchJob?.cancel()
        getFreeToWatchJob = viewModelScope.launch {
            state =
                state.copy(
                    isLoading = true,
                    isFreeToWatchMediaLoadingError = false,
                    freeToWatchFilterSelected = freeToWatchFilter,
                    freeToWatchMediaItemList = null
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