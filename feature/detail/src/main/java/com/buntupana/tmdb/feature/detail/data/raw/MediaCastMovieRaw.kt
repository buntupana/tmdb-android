package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class MediaCastMovieRaw(
    val id: Long,
    val adult: Boolean,
    val character: String,
    @Json(name = "credit_id")
    val creditId: String,
    val gender: Int,
    @Json(name = "known_for_department")
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    @Json(name = "original_name")
    val originalName: String,
    val popularity: Double,
    @Json(name = "profile_path")
    val profilePath: String?
)