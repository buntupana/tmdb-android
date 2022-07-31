package com.buntupana.tmdb.feature.search.data.raw

import com.buntupana.tmdb.core.data.raw.KnownFor
import com.squareup.moshi.Json

data class PersonRaw(
    val id: Long,
    val adult: Boolean,
    val gender: Int,
    @field:Json(name = "known_for")
    val knownFor: List<KnownFor>?,
    @field:Json(name = "known_for_department")
    val knownForDepartment: String?,
    val name: String,
    val popularity: Double,
    @field:Json(name = "profile_path")
    val profilePath: String?
)