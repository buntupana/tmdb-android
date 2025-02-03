package com.buntupana.tmdb.feature.account.data.raw


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("media_id")
    val mediaId: Int,
    @SerialName("media_type")
    val mediaType: String,
    @SerialName("success")
    val success: Boolean
)