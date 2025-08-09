package com.buntupana.tmdb.feature.discover.presentation.movies

import com.buntupana.tmdb.feature.discover.domain.entity.MediaListFilter
import com.buntupana.tmdb.feature.discover.domain.entity.ReleaseType
import com.buntupana.tmdb.feature.discover.domain.entity.SortBy
import java.time.LocalDate

object MediaFilterMovieDefault {

    val popular = MediaListFilter(
        sortBy = SortBy.POPULARITY_DESC,
        releaseDateTo = LocalDate.now()
            .plusMonths(6)
    )

    val nowPlaying = MediaListFilter(
        sortBy = SortBy.POPULARITY_DESC,
        releaseTypeList = listOf(ReleaseType.THEATRICAL),
        releaseDateFrom = LocalDate.now()
            .minusMonths(1)
            .minusWeeks(2),
        releaseDateTo = LocalDate.now().plusDays(7)
    )

    val upcoming = MediaListFilter(
        sortBy = SortBy.POPULARITY_DESC,
        releaseTypeList = listOf(ReleaseType.THEATRICAL),
        releaseDateFrom = LocalDate.now().plusDays(7),
        releaseDateTo = LocalDate.now().plusDays(21)
    )

    val topRated = MediaListFilter(
        sortBy = SortBy.RATING_DESC,
        releaseDateTo = LocalDate.now()
            .plusMonths(6),
        minUserVotes = 300
    )
}