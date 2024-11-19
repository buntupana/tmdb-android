package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.Serializable

@Serializable
data class MediaVideosRaw(
    val results: List<VideoRaw>
)