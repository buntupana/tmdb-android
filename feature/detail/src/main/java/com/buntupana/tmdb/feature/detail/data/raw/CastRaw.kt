package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class CastRaw(
    val adult: Boolean,
    val character: String,
    @field:Json(name = "credit_id")
    val creditId: String,
    val gender: Int,
    val id: Long,
    @field:Json(name = "known_for_department")
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    @field:Json(name = "original_name")
    val originalName: String,
    val popularity: Double,
    @field:Json(name = "profile_path")
    val profilePath: String
)