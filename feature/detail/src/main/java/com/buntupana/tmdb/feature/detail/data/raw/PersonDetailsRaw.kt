package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class PersonDetailsRaw(
    val id: Long,
    val name: String,
    val adult: Boolean,
    @field:Json(name = "also_known_as")
    val alsoKnownAs: List<String>,
    val biography: String?,
    val birthday: String?,
    @field:Json(name = "deathday")
    val deathDay: String?,
    val gender: Int,
    val homepage: String?,
    @field:Json(name = "imdb_id")
    val imdbId: String?,
    @field:Json(name = "known_for_department")
    val knownForDepartment: String?,
    @field:Json(name = "place_of_birth")
    val placeOfBirth: String?,
    val popularity: Double,
    @field:Json(name = "profile_path")
    val profilePath: String?
)