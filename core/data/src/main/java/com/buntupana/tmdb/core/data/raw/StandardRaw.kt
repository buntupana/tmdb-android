package com.buntupana.tmdb.core.data.raw


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StandardRaw(
    @SerialName("status_code")
    val statusCode: Int,
    @SerialName("status_message")
    val statusMessage: String,
    @SerialName("success")
    val success: Boolean
)