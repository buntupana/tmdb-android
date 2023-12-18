package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class RoleRaw(
    @field:Json(name = "credit_id")
    val creditId: String,
    val character: String,
    @field:Json(name = "episode_count")
    val episodeCount: Int
)