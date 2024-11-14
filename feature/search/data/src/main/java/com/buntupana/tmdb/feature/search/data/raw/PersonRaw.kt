package com.buntupana.tmdb.feature.search.data.raw

import com.buntupana.tmdb.data.raw.KnownFor
import com.squareup.moshi.Json

data class PersonRaw(
    val id: Long,
    val adult: Boolean,
    val gender: Int,
    @Json(name = "known_for")
    val knownFor: List<KnownFor>?,
    @Json(name = "known_for_department")
    val knownForDepartment: String?,
    val name: String,
    val popularity: Double,
    @Json(name = "profile_path")
    val profilePath: String?
)