package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class MediaCrewTvShowRaw(
    val id: Long,
    val adult: Boolean,
    val department: String,
    val gender: Int,
    val jobs: List<JobRaw>,
    @field:Json(name = "known_for_department")
    val knownForDepartment: String,
    val name: String,
    @field:Json(name = "original_name")
    val originalName: String,
    val popularity: Double,
    @field:Json(name = "profile_path")
    val profilePath: String,
    @field:Json(name = "total_episode_count")
    val totalEpisodeCount: Int
)