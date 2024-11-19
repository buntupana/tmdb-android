package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.Serializable

@Serializable
data class ReleaseDatesRaw(
    val results: List<ReleaseDateCountry>
)