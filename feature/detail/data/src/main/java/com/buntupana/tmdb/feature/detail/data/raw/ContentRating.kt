package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.Serializable

@Serializable
data class ContentRating(
    val iso_3166_1: String,
    val rating: String
)