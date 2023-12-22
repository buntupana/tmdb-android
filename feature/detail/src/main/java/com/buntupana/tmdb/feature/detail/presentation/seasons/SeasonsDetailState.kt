package com.buntupana.tmdb.feature.detail.presentation.seasons

import androidx.compose.ui.graphics.Color
import com.buntupana.tmdb.feature.detail.domain.model.Season

data class SeasonsDetailState(
    val mediaId: Long,
    val mediaName: String,
    val posterUrl: String?,
    val releaseYear: String?,
    val backgroundColor: Color,
    val seasonList: List<Season>
)
