package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonImagesRaw(
    @SerialName("id")
    val id: Int,
    @SerialName("profiles")
    val profiles: List<Profile>
)