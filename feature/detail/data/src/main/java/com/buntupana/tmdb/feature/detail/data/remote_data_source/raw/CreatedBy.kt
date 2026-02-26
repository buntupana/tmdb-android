package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatedBy(
    val id: Long,
    @SerialName("credit_id")
    val creditId: String? = null,
    val gender: Int? = null,
    val name: String? = null,
    @SerialName("profile_path")
    val profilePath: String? = null
)