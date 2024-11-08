package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class MediaCrewMovieRaw(
    val id: Long,
    val adult: Boolean,
    @Json(name = "credit_id")
    val creditId: String,
    val department: String,
    val gender: Int,
    val job: String,
    @Json(name = "known_for_department")
    val knownForDepartment: String,
    val name: String,
    @Json(name = "original_name")
    val originalName: String,
    val popularity: Double,
    @Json(name = "profile_path")
    val profilePath: String?
)