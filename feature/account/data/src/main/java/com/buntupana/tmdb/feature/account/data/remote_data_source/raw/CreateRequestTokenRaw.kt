package com.buntupana.tmdb.feature.account.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRequestTokenRaw(
    val success: Boolean,
    @SerialName("request_token")
    val requestToken: String,
    @SerialName("status_code")
    val statusCode: Int
)