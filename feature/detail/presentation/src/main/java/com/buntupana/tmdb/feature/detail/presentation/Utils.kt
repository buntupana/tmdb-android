package com.buntupana.tmdb.feature.detail.presentation

import androidx.compose.ui.graphics.Color
import com.buntupana.tmdb.core.ui.theme.HighScoreColor
import com.buntupana.tmdb.core.ui.theme.LowScoreColor
import com.buntupana.tmdb.core.ui.theme.MediumScoreColor
import com.buntupana.tmdb.core.ui.theme.NoScoreColor

fun getRatingColor(rating: Int): Color {
    return when (rating) {
        in 0..30 -> LowScoreColor
        in 4..60 -> MediumScoreColor
        in 7..100 -> HighScoreColor
        else -> NoScoreColor
    }
}