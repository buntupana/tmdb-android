package com.buntupana.tmdb.core.data.raw


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModifiedByRaw(
    @SerialName("avatar")
    val avatar: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("email")
    val email: String,
    @SerialName("id")
    val id: Int,
    @SerialName("jellyfinAuthToken")
    val jellyfinAuthToken: String,
    @SerialName("permissions")
    val permissions: Int,
    @SerialName("plexToken")
    val plexToken: String,
    @SerialName("plexUsername")
    val plexUsername: String,
    @SerialName("requestCount")
    val requestCount: Int,
    @SerialName("updatedAt")
    val updatedAt: String,
    @SerialName("userType")
    val userType: Int,
    @SerialName("username")
    val username: String
)