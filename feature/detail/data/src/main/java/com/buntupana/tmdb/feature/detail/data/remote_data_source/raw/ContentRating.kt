package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.Serializable

@Serializable
data class ContentRating(
    val iso_3166_1: String,
    val rating: String
)