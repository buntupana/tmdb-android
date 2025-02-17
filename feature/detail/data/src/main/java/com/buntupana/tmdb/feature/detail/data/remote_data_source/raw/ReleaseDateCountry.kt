package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReleaseDateCountry(
    val iso_3166_1: String,
    @SerialName("release_dates")
    val releaseDates: List<ReleaseDateRaw>
)