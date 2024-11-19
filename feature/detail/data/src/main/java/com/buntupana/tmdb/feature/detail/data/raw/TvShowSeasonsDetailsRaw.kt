package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.Serializable

@Serializable
data class TvShowSeasonsDetailsRaw(
    val seasons: List<SeasonRaw>? = null
)