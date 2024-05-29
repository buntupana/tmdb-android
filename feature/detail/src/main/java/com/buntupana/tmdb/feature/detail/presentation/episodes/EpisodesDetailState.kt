package com.buntupana.tmdb.feature.detail.presentation.episodes

import androidx.compose.ui.graphics.Color

data class EpisodesDetailState(
    val isLoading: Boolean = false,
    val mediaId: Long,
    val mediaName: String,
    val posterUrl: String?,
    val releaseYear: String?,
    val backgroundColor: Color,
)
