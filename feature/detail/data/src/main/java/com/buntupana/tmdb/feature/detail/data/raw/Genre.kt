package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val name: String
)