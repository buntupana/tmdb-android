package com.buntupana.tmdb.feature.detail.presentation.episodes

import com.buntupana.tmdb.core.ui.navigation.Routes
import kotlinx.serialization.Serializable

@Serializable
data class EpisodesDetailNav(
    val tvShowId: Long,
    val seasonName: String,
    val seasonNumber: Int,
    val posterUrl: String?,
    val backgroundColor: Int?,
    val releaseYear: String?
) : Routes
