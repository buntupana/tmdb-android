package com.buntupana.tmdb.feature.account.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRequestTokenRaw(
    val success: Boolean,
    @SerialName("request_token")
    val requestToken: String,
    @SerialName("expires_at")
    val expiresAt: String
)