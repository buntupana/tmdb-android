package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaCastGuestStarRaw(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("character")
    val character: String,
    @SerialName("adult")
    val adult: Boolean = false,
    @SerialName("credit_id")
    val creditId: String,
    @SerialName("gender")
    val gender: Int? = null,
    @SerialName("known_for_department")
    val knownForDepartment: String? = null,
    @SerialName("order")
    val order: Int,
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("popularity")
    val popularity: Float? = null,
    @SerialName("profile_path")
    val profilePath: String? = null
)