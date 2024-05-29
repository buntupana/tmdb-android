package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class JobRaw(
    @Json(name = "credit_id")
    val creditId: String,
    @Json(name = "episode_count")
    val episodeCount: Int?,
    val job: String
)