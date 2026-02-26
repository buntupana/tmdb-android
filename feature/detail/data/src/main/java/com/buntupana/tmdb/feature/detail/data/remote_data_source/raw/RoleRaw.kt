package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

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