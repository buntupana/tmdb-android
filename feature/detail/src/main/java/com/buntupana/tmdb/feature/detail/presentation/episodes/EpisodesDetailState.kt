package com.buntupana.tmdb.feature.detail.presentation.episodes

import androidx.compose.ui.graphics.Color
import com.buntupana.tmdb.feature.detail.domain.model.Episode

data class EpisodesDetailState(
    val isLoading: Boolean = false,
    val isGetEpisodesError: Boolean = false,
    val tvShowId: Long,
    val sessionName: String = "",
    val seasonNumber: Int,
    val posterUrl: String? = null,
    val releaseYear: String? = null,
    val backgroundColor: Color,
    val episodeList: List<Episode>? = null
)
