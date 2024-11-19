package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoRaw(
    val id: String,
    val iso_3166_1: String? = null,
    val iso_639_1: String? = null,
    val key: String? = null,
    val name: String? = null,
    val official: Boolean? = null,
    @SerialName("published_at")
    val publishedAt: String? = null,
    val site: String? = null,
    val size: Int? = null,
    val type: String? = null
)