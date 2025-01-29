package com.buntupana.tmdb.feature.account.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateAccessTokenRaw(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("account_id")
    val accountId: String,
    @SerialName("status_code")
    val statusCode: Int,
    @SerialName("status_message")
    val statusMessage: String,
    val success: Boolean
)