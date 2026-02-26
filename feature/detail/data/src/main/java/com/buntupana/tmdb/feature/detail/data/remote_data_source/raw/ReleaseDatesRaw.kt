package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.Serializable

@Serializable
data class ReleaseDatesRaw(
    val results: List<ReleaseDateCountry>
)