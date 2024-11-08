package com.buntupana.tmdb.feature.detail.domain.model

import java.time.LocalDate


data class Video(
    val id: String,
    val name: String,
    val url: String,
    val videoType: VideoType,
    val publishedAt: LocalDate?,
    val official: Boolean
)
