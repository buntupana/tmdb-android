package com.buntupana.tmdb.feature.account.data.remote_data_source.request

import kotlinx.serialization.Serializable

@Serializable
data class AddRatingRequest(
    val value: Float
)
