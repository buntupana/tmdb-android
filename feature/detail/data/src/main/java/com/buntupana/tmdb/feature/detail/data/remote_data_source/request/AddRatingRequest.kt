package com.buntupana.tmdb.feature.detail.data.remote_data_source.request

import kotlinx.serialization.Serializable

@Serializable
data class AddRatingRequest(
    val value: Float
)