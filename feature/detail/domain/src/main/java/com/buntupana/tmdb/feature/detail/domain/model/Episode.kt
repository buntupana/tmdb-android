package com.buntupana.tmdb.feature.detail.domain.model

import java.time.LocalDate

data class Episode(
    val id: Long,
    val showId: Long?,
    val name: String,
    val airDate: LocalDate?,
    val episodeNumber: Int,
    val overview: String?,
    val runtime: Int?,
    val seasonNumber: Int?,
    val stillUrl: String?,
    val voteAverage: Float?,
    val voteCount: Int?,
    val userRating: Int?
)
