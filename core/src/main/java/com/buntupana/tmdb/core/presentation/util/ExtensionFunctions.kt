package com.buntupana.tmdb.core.presentation.util

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.buntupana.tmdb.core.presentation.theme.Dimens
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


/** Return a black/white color that will be readable on top */
fun Color.getOnBackgroundColor(): Color {
    return if (luminance() > 0.5f) Color.Black else Color.White
}

fun <T> LazyGridScope.gridItems(
    data: List<T>,
    columnCount: Int,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val size = data.count()
    val rows = if (size == 0) 0 else 1 + (size - 1) / columnCount
    items(rows, key = { it.hashCode() }) { rowIndex ->
        Row(
            horizontalArrangement = horizontalArrangement,
            modifier = modifier
        ) {
            for (columnIndex in 0 until columnCount) {
                val itemIndex = rowIndex * columnCount + columnIndex
                if (itemIndex < size) {
                    Box(
                        modifier = Modifier.weight(1F, fill = true),
                        propagateMinConstraints = true
                    ) {
                        itemContent(data[itemIndex])
                    }
                } else {
                    Spacer(Modifier.weight(1F, fill = true))
                }
            }
        }
    }
}

fun LocalDate.toLocalFormat(): String {
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(
        java.util.Locale.getDefault()
    )
    return this.format(dateFormatter)
}

fun <T> T?.ifNullAux(block: () -> T?) = this ?: block()

fun <T> T?.ifNull(block: () -> T) = this ?: block()

fun <T> Any?.ifNotNull(block: () -> T?) = if (this != null) block() else null

fun String?.ifNullOrBlank(block: () -> String): String {
    return if (this.isNullOrBlank()) {
        block()
    } else {
        this
    }
}

fun Modifier.clickableTextPadding(): Modifier {
    return padding(horizontal = Dimens.padding.medium, vertical = Dimens.padding.small)
}

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