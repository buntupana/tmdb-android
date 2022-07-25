package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class ReleaseDateRaw(
    val certification: String,
    val iso_639_1: String,
    val note: String,
    @field:Json(name = "release_date")
    val releaseDate: String,
    val type: Int
)