package com.buntupana.tmdb.feature.lists.data.remote_data_source.raw


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