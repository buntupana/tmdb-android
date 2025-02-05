package com.buntupana.tmdb.feature.account.data.remote_data_source.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionRequest(
    @SerialName("access_token")
    val accessToken: String
)
