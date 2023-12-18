package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class JobRaw(
    @field:Json(name = "credit_id")
    val creditId: String,
    @field:Json(name = "episode_count")
    val episodeCount: Int,
    val job: String
)