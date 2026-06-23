package com.buntupana.tmdb.feature.seerr.data.remote_data_source.request

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.Serializable

@Serializable
data class LocalLoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class JellyfinLoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class CreateMediaRequestBody(
    val mediaType: String,
    val mediaId: Long,
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val seasons: List<Int>? = null
)
