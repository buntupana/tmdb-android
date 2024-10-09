package com.buntupana.tmdb.feature.detail.presentation.seasons

import androidx.compose.ui.graphics.Color
import com.buntupana.tmdb.feature.detail.domain.model.Season

data class SeasonsDetailState(
    val isLoading: Boolean = false,
    val isGetSessionsError: Boolean = false,
    val tvShowId: Long,
    val tvShowName: String = "",
    val posterUrl: String? = null,
    val releaseYear: String? = null,
    val backgroundColor: Color,
    val seasonList: List<Season>? = null
)
