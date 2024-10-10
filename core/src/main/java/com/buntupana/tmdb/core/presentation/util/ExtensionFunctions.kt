package com.buntupana.tmdb.core.presentation.util

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.palette.graphics.Palette
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract


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

fun LocalDate.toFullDate(): String {
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(
        java.util.Locale.getDefault()
    )
    return this.format(dateFormatter)
}

@OptIn(ExperimentalContracts::class)
fun CharSequence?.isNotNullOrBlank(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrBlank != null)
    }
    return this.isNullOrBlank().not()
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

fun String?.ifNotNullOrBlank(block: () -> String): String? {
    return if (this.isNullOrBlank()) {
        this
    } else {
        block()
    }
}

fun String.encodeUrl(): String {
    return URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
}

fun String.decodeUrl(): String {
    return URLDecoder.decode(this, StandardCharsets.UTF_8.toString())
}

fun formatTime(time: Int): String {
    var result = ""
    val hours = time / 60
    val minutes = time % 60

    if (hours != 0) {
        result += "${hours}h "
    }
    if (minutes != 0) {
        result += "${minutes}m"
    }

    return result
}

@OptIn(ExperimentalContracts::class)
fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrEmpty != null)
    }
    return this.isNullOrEmpty().not()
}

@OptIn(ExperimentalContracts::class)
fun <K, V> Map<out K, V>?.isNotNullOrEmpty(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrEmpty != null)
    }
    return this.isNullOrEmpty().not()
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

fun <T>SavedStateHandle.navArgs(): T {
    return get<T>("args")!!
}