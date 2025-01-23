package com.buntupana.tmdb.feature.account.data.request

import kotlinx.serialization.Serializable

@Serializable
data class AddRatingRequest(
    val value: Float
)
