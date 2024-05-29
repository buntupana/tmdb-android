package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class RoleRaw(
    @Json(name = "credit_id")
    val creditId: String,
    val character: String,
    @Json(name = "episode_count")
    val episodeCount: Int?
)