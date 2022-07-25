package com.buntupana.tmdb.core.presentation.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

/** Return a black/white color that will be readable on top */
fun Color.getBinaryForegroundColor() : Color {
    return if (toArgb().red * 0.299 + toArgb().green * 0.587 + toArgb().blue * 0.114 > 186) Color.Black else Color.White
}