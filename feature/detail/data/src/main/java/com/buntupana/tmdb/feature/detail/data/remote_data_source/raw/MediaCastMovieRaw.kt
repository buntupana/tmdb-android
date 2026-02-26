package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaCastMovieRaw(
    val id: Long,
    val adult: Boolean,
    val character: String,
    @SerialName("credit_id")
    val creditId: String,
    val gender: Int,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String? = null
)