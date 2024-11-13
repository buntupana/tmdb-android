package com.buntupana.tmdb.feature.account.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionRaw(
    val success: Boolean,
    @SerialName("session_id")
    val sessionId: String
)