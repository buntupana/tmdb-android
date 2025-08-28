package com.buntupana.tmdb.feature.discover.presentation.media_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.buntupana.tmdb.core.ui.util.navArgs
import com.buntupana.tmdb.feature.discover.domain.entity.MovieListFilter
import com.buntupana.tmdb.feature.discover.domain.entity.SortBy
import com.buntupana.tmdb.feature.discover.domain.entity.TvShowListFilter
import com.buntupana.tmdb.feature.discover.domain.usecase.GetFilteredMoviesPagingUseCase
import com.buntupana.tmdb.feature.discover.domain.usecase.GetFilteredTvShowsPagingUseCase
import com.buntupana.tmdb.feature.discover.presentation.media_filter.SortByOrder
import com.buntupana.tmdb.feature.discover.presentation.media_filter.SortBySimple
import com.buntupana.tmdb.feature.discover.presentation.media_list.filters.MediaFilterListDefault
import com.buntupana.tmdb.feature.discover.presentation.media_list.filters.MovieDefaultFilter
import com.buntupana.tmdb.feature.discover.presentation.media_list.filters.TvShowDefaultFilter
import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter
import com.panabuntu.tmdb.core.common.entity.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MediaListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFilteredMoviesPagingUseCase: GetFilteredMoviesPagingUseCase,
    private val getFilteredTvShowsPagingUseCase: GetFilteredTvShowsPagingUseCase
) : ViewModel() {

    // it can be used MediaListNav.Movie or MediaListNav.TvShow because they have the same parameter
    // There are two of them to treat them as different routes but the screen is the same
    private val navArgs = savedStateHandle.navArgs<MediaListNav.Movie>()

    var state by mutableStateOf(
        MediaListState(
            mediaType = navArgs.mediaType,
            mediaListFilter = when (navArgs.mediaType) {
                MediaType.MOVIE -> MediaFilterListDefault.popularMovie
                MediaType.TV_SHOW -> MediaFilterListDefault.popularTvShow
            }
        )
    )
        private set

    init {
        onEvent(MediaListEvent.FilterMediaList(mediaListFilter = state.mediaListFilter))
    }

    fun onEvent(event: MediaListEvent) {
        Timber.d("onEvent() called with: event = $event")
        viewModelScope.launch {
            when (event) {
                is MediaListEvent.FilterMediaList -> {
                    when (event.mediaListFilter) {
                        is MediaListFilter.Movie -> getMovies(event.mediaListFilter)
                        is MediaListFilter.TvShow -> getTvShows(event.mediaListFilter)
                    }
                }
            }
        }
    }

    private suspend fun getMovies(mediaListFilter: MediaListFilter.Movie) {

        if (state.movieItems != null && state.mediaListFilter == mediaListFilter) return

        val movieDefaultFilter = when (mediaListFilter) {
            MediaFilterListDefault.popularMovie -> MovieDefaultFilter.POPULAR
            MediaFilterListDefault.nowPlayingMovie -> MovieDefaultFilter.NOW_PLAYING
            MediaFilterListDefault.topRatedMovie -> MovieDefaultFilter.TOP_RATED
            MediaFilterListDefault.upcomingMovie -> MovieDefaultFilter.UPCOMING
            else -> MovieDefaultFilter.CUSTOM
        }

        state = state.copy(
            mediaListFilter = mediaListFilter,
            movieDefaultFilter = movieDefaultFilter,
            movieItems = null
        )

        val sortBy = getSortBy(
            sortBySimple = mediaListFilter.sortBy,
            sortByOrder = mediaListFilter.sortByOrder
        )

        val movieListFilter = MovieListFilter(
            sortBy = sortBy,
            releaseTypeList = mediaListFilter.releaseTypeList,
            monetizationTypeList = mediaListFilter.monetizationTypeList,
            releaseDateFrom = mediaListFilter.releaseDateFrom,
            releaseDateTo = mediaListFilter.releaseDateTo,
            genreList = mediaListFilter.genreList,
            language = mediaListFilter.language,
            userScoreRange = IntRange(mediaListFilter.userScoreMin, mediaListFilter.userScoreMax),
            includeNotRated = mediaListFilter.includeNotRated,
            minUserVotes = mediaListFilter.minUserVotes,
            runTimeRange = IntRange(mediaListFilter.runtimeMin, mediaListFilter.runtimeMax),
        )

        getFilteredMoviesPagingUseCase(movieListFilter = movieListFilter).let {
            state = state.copy(movieItems = it.cachedIn(viewModelScope))
        }
    }

    private suspend fun getTvShows(mediaListFilter: MediaListFilter.TvShow) {

        if (state.tvShowItems != null && state.mediaListFilter == mediaListFilter) return

        val tvShowDefaultFilter = when (mediaListFilter) {
            MediaFilterListDefault.popularTvShow -> TvShowDefaultFilter.POPULAR
            MediaFilterListDefault.airingTodayTvShow -> TvShowDefaultFilter.AIRING_TODAY
            MediaFilterListDefault.onTvTvShow -> TvShowDefaultFilter.ON_TV
            MediaFilterListDefault.topRatedTvShow -> TvShowDefaultFilter.TOP_RATED
            else -> TvShowDefaultFilter.CUSTOM
        }

        state = state.copy(
            mediaListFilter = mediaListFilter,
            tvShowDefaultFilter = tvShowDefaultFilter,
            tvShowItems = null
        )

        val sortBy = getSortBy(
            sortBySimple = mediaListFilter.sortBy,
            sortByOrder = mediaListFilter.sortByOrder
        )

        val tvShowListFilter = TvShowListFilter(
            sortBy = sortBy,
            monetizationTypeList = mediaListFilter.monetizationTypeList,
            releaseDateFrom = mediaListFilter.releaseDateFrom,
            releaseDateTo = mediaListFilter.releaseDateTo,
            searchFirstAirDate = mediaListFilter.searchFirstAirDate,
            genreList = mediaListFilter.genreList,
            language = mediaListFilter.language,
            userScoreRange = IntRange(mediaListFilter.userScoreMin, mediaListFilter.userScoreMax),
            includeNotRated = mediaListFilter.includeNotRated,
            minUserVotes = mediaListFilter.minUserVotes,
            runTimeRange = IntRange(mediaListFilter.runtimeMin, mediaListFilter.runtimeMax),
        )

        getFilteredTvShowsPagingUseCase(tvShowListFilter = tvShowListFilter).let {
            state = state.copy(tvShowItems = it.cachedIn(viewModelScope))
        }
    }

    private fun getSortBy(sortBySimple: SortBySimple, sortByOrder: SortByOrder): SortBy {
        return when (sortBySimple) {
            SortBySimple.POPULARITY -> {
                when (sortByOrder) {
                    SortByOrder.ASCENDING -> SortBy.POPULARITY_ASC
                    SortByOrder.DESCENDING -> SortBy.POPULARITY_DESC
                }
            }

            SortBySimple.RATING -> {
                when (sortByOrder) {
                    SortByOrder.ASCENDING -> SortBy.RATING_ASC
                    SortByOrder.DESCENDING -> SortBy.RATING_DESC
                }
            }

            SortBySimple.RELEASE_DATE -> {
                when (sortByOrder) {
                    SortByOrder.ASCENDING -> SortBy.RELEASE_DATE_ASC
                    SortByOrder.DESCENDING -> SortBy.RELEASE_DATE_DESC
                }
            }

            SortBySimple.TITLE -> {
                when (sortByOrder) {
                    SortByOrder.ASCENDING -> SortBy.TITLE_ASC
                    SortByOrder.DESCENDING -> SortBy.TITLE_DESC
                }
            }
        }
    }
}