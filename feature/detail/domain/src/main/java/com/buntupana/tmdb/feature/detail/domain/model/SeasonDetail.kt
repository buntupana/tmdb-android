package com.buntupana.tmdb.feature.detail.domain.model

import java.time.LocalDate

data class SeasonDetail(
    val id: Int,
    val name: String,
    val overview: String?,
    val posterUrl: String?,
    val airDate: LocalDate?,
    val episodes: List<Episode>,
    val seasonNumber: Int,
    val voteAverage: Float
)
