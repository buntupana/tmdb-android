package com.buntupana.tmdb.feature.detail.presentation.seasons

import com.buntupana.tmdb.core.presentation.navigation.Routes
import kotlinx.serialization.Serializable

@Serializable
data class SeasonsDetailNav(
    val tvShowId: Long,
    val tvShowName: String,
    val releaseYear: String?,
    val posterUrl: String?,
    val backgroundColor: Int?
) : Routes
