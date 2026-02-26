package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaImagesRaw(
    @SerialName("id")
    val id: Int,
    @SerialName("backdrops")
    val backdrops: List<Backdrop> = emptyList(),
    @SerialName("logos")
    val logos: List<Logo> = emptyList(),
    @SerialName("posters")
    val posters: List<Poster> = emptyList()
)