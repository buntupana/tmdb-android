package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaCastTvShowRaw(
    val id: Long,
    val adult: Boolean,
    val gender: Int,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String? = null,
    val roles: List<RoleRaw>? = null,
    @SerialName("total_episode_count")
    val totalEpisodeCount: Int? = null
)