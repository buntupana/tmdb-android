package com.buntupana.tmdb.feature.search.data.raw

import com.buntupana.tmdb.core.data.raw.KnownFor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonRaw(
    val id: Long,
    val adult: Boolean,
    val gender: Int,
    @SerialName("known_for")
    val knownFor: List<KnownFor>? = null,
    @SerialName("known_for_department")
    val knownForDepartment: String? = null,
    val name: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String? = null
)