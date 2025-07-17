package com.buntupana.tmdb.feature.discover.domain.entity

import java.time.LocalDate

data class MediaFilter(
    val sortBy: SortBy? = null,
    val releaseTypeList: List<ReleaseType>? = null,
    val monetizationType: MonetizationType? = null,
    val releaseDateFrom: LocalDate? = null,
    val releaseDateTo: LocalDate? = null,
    val genreList: List<Genre>? = null,
    val language: String? = null,
    val userScoreMax: Int? = null,
    val userScoreMin: Int? = null,
    val minUserVotes: Int? = null,
    val runtime: Long? = null,
    val minVoteCount: Int? = null,
    val minRating: Int? = null,
    val maxRating: Int? = null,
)