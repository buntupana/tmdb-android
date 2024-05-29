package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class MediaCastTvShowRaw(
    val id: Long,
    val adult: Boolean,
    val gender: Int,
    @Json(name = "known_for_department")
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    @Json(name = "original_name")
    val originalName: String,
    val popularity: Double,
    @Json(name = "profile_path")
    val profilePath: String?,
    val roles: List<RoleRaw>,
    @Json(name = "total_episode_count")
    val totalEpisodeCount: Int
)