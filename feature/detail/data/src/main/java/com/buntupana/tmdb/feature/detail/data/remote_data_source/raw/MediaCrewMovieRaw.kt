package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaCrewMovieRaw(
    val id: Long,
    val adult: Boolean,
    @SerialName("credit_id")
    val creditId: String,
    val department: String,
    val gender: Int,
    val job: String,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String? = null
)