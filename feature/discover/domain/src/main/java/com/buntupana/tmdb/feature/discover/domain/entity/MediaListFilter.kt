package com.buntupana.tmdb.feature.discover.domain.entity

import java.time.LocalDate

data class MediaListFilter(
    val sortBy: SortBy = SortBy.POPULARITY_DESC,
    val releaseTypeList: List<ReleaseType> = emptyList(),
    val monetizationTypeList: List<MonetizationType> = emptyList(),
    val releaseDateFrom: LocalDate? = null,
    val releaseDateTo: LocalDate? = null,
    val genreList: List<Genre> = emptyList(),
    val language: String? = null,
    val userScoreMax: Int? = null,
    val userScoreMin: Int? = null,
    val minUserVotes: Int? = null,
    val runtime: Long? = null,
    val minVoteCount: Int? = null,
    val minRating: Int? = null,
    val maxRating: Int? = null,
)