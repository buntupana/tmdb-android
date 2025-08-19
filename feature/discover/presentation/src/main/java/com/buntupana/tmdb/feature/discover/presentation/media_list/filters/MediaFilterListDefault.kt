package com.buntupana.tmdb.feature.discover.presentation.media_list.filters

import com.buntupana.tmdb.feature.discover.domain.entity.ReleaseType
import com.buntupana.tmdb.feature.discover.presentation.media_filter.SortByOrder
import com.buntupana.tmdb.feature.discover.presentation.media_filter.SortBySimple
import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter
import java.time.LocalDate

object MediaFilterListDefault {


    val popularMovie = MediaListFilter.Movie(
        _sortBy = SortBySimple.POPULARITY,
        _sortByOrder = SortByOrder.DESCENDING,
        _releaseDateTo = LocalDate.now()
            .plusMonths(6)
    )

    val popularTvShow = MediaListFilter.TvShow(
        _sortBy = SortBySimple.POPULARITY,
        _sortByOrder = SortByOrder.DESCENDING,
        _releaseDateTo = LocalDate.now()
            .plusMonths(6)
    )

    val nowPlayingMovie = MediaListFilter.Movie(
        _sortBy = SortBySimple.POPULARITY,
        _sortByOrder = SortByOrder.DESCENDING,
        releaseTypeList = listOf(ReleaseType.THEATRICAL),
    )

    val airingTodayTvShow = MediaListFilter.TvShow(
        _sortBy = SortBySimple.POPULARITY,
        _sortByOrder = SortByOrder.DESCENDING,
        _releaseDateFrom = LocalDate.now(),
        _releaseDateTo = LocalDate.now()
    )

    val upcomingMovie = MediaListFilter.Movie(
        _sortBy = SortBySimple.POPULARITY,
        _sortByOrder = SortByOrder.DESCENDING,
        releaseTypeList = listOf(ReleaseType.THEATRICAL),
        _releaseDateFrom = LocalDate.now().plusDays(7),
        _releaseDateTo = LocalDate.now().plusDays(21)
    )

    val onTvTvShow = MediaListFilter.TvShow(
        _sortBy = SortBySimple.POPULARITY,
        _sortByOrder = SortByOrder.DESCENDING,
        _releaseDateFrom = LocalDate.now(),
        _releaseDateTo = LocalDate.now().plusDays(7)
    )

    val topRatedMovie = MediaListFilter.Movie(
        _sortBy = SortBySimple.RATING,
        _sortByOrder = SortByOrder.DESCENDING,
        _releaseDateTo = LocalDate.now()
            .plusMonths(6),
        _minUserVotes = 300
    )

    val topRatedTvShow = MediaListFilter.TvShow(
        _sortBy = SortBySimple.RATING,
        _sortByOrder = SortByOrder.DESCENDING,
        _releaseDateTo = LocalDate.now()
            .plusMonths(6),
        _minUserVotes = 200
    )
}