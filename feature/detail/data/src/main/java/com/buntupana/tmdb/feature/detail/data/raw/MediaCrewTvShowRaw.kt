package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaCrewTvShowRaw(
    val id: Long,
    val adult: Boolean,
    val department: String,
    val gender: Int,
    val jobs: List<JobRaw>,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String? = null,
    @SerialName("total_episode_count")
    val totalEpisodeCount: Int
)