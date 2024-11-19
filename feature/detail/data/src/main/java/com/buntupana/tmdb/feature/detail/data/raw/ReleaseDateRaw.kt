package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReleaseDateRaw(
    val certification: String,
    val iso_639_1: String,
    val note: String,
    @SerialName("release_date")
    val releaseDate: String,
    val type: Int
)