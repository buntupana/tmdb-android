package com.buntupana.tmdb.feature.discover.domain.entity

import java.time.LocalDate

data class TvShowListFilter(
    val sortBy: SortBy = SortBy.POPULARITY_DESC,
    val monetizationTypeList: List<MonetizationType> = emptyList(),
    val releaseDateFrom: LocalDate? = null,
    val releaseDateTo: LocalDate? = null,
    val genreList: List<TvShowGenre> = emptyList(),
    val language: String? = null,
    val userScoreRange: IntRange = IntRange(0, 100),
    val includeNotRated: Boolean = true,
    val minUserVotes: Int = 0,
    val runTimeRange: IntRange = IntRange(0, 390)
)