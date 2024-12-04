package com.buntupana.tmdb.feature.detail.data.request

import kotlinx.serialization.Serializable

@Serializable
data class AddRatingRequest(
    val value: Float
)
