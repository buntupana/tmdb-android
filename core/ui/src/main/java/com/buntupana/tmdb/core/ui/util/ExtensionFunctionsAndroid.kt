package com.buntupana.tmdb.core.ui.util

import android.graphics.drawable.Drawable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import androidx.palette.graphics.Palette
import com.buntupana.tmdb.core.ui.navigation.Routes
import com.panabuntu.tmdb.core.common.decodeAllStrings

fun Drawable.getDominantColor(colorResult: (dominantColor: Color) -> Unit) {
    Palette.Builder(toBitmap()).generate { palette ->
        if (palette == null) {
            return@generate
        }
        palette.dominantSwatch?.rgb?.let { dominantColor ->
            colorResult(Color(dominantColor))
        }
    }
}

/** Return a black/white color that will be readable on top */
fun Color.getOnBackgroundColor(): Color {
    return if (luminance() > 0.5f) Color.Black else Color.White
}

fun Modifier.brush(brush: Brush) = this
    .graphicsLayer(alpha = 0.99f)
    .drawWithCache {
        onDrawWithContent {
            drawContent()
            drawRect(brush, blendMode = BlendMode.SrcAtop)
        }
    }

inline fun <reified T : Routes> SavedStateHandle.navArgs(): T {
    return toRoute<T>().decodeAllStrings()
}