package com.buntupana.tmdb.feature.seerr.data.remote_data_source.raw

import kotlinx.serialization.Serializable

@Serializable
data class SeerrMediaRaw(
    val mediaInfo: SeerrMediaInfoRaw? = null,
    val seasons: List<SeerrSeasonRaw>? = null,
)
