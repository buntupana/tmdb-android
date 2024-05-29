package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class BelongsToCollection(
    val id: Int,
    val name: String,
    @Json(name = "backdrop_path")
    val backdropPath: String,
    @Json(name = "poster_path")
    val posterPath: String?
)