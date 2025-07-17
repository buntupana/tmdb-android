package com.buntupana.tmdb.feature.discover.presentation.movies

import com.buntupana.tmdb.feature.discover.domain.entity.MediaFilter
import com.buntupana.tmdb.feature.discover.domain.entity.ReleaseType
import com.buntupana.tmdb.feature.discover.domain.entity.SortBy
import java.time.LocalDate

object MediaFilterMovieDefault {

    val popular = MediaFilter(
        sortBy = SortBy.POPULARITY_DESC,
    )

    val nowPlaying = MediaFilter(
        sortBy = SortBy.POPULARITY_DESC,
        releaseTypeList = listOf(ReleaseType.THEATRICAL),
        releaseDateFrom = LocalDate.now()
            .minusMonths(1)
            .minusWeeks(2),
        releaseDateTo = LocalDate.now().plusDays(7)
    )

    val upcoming = MediaFilter(
        sortBy = SortBy.POPULARITY_DESC,
        releaseTypeList = listOf(ReleaseType.THEATRICAL),
        releaseDateFrom = LocalDate.now().plusDays(7),
        releaseDateTo = LocalDate.now().plusDays(21)
    )

    val topRated = MediaFilter(
        sortBy = SortBy.RATING_DESC,
        minVoteCount = 300
    )
}