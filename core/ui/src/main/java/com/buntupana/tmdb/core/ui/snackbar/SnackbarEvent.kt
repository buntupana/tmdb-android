package com.buntupana.tmdb.core.ui.snackbar

import androidx.compose.material3.SnackbarDuration
import com.buntupana.tmdb.core.ui.util.UiText

data class SnackbarEvent(
    val message: UiText,
    val action: SnackbarAction? = null,
    val snackbarDuration: SnackbarDuration = SnackbarDuration.Short
)
