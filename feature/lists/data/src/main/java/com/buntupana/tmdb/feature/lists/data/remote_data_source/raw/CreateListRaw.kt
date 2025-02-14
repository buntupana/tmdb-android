package com.buntupana.tmdb.feature.lists.data.remote_data_source.raw


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateListRaw(
    @SerialName("id")
    val id: Long,
    @SerialName("status_code")
    val statusCode: Int,
    @SerialName("status_message")
    val statusMessage: String,
    @SerialName("success")
    val success: Boolean
)