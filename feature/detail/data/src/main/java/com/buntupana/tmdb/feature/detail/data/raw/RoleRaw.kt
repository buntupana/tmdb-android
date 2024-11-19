package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoleRaw(
    @SerialName("credit_id")
    val creditId: String,
    val character: String,
    @SerialName("episode_count")
    val episodeCount: Int? = null
)