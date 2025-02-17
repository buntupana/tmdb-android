package com.buntupana.tmdb.core.ui.util

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.WindowCompat
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.toRoute
import androidx.palette.graphics.Palette
import com.buntupana.tmdb.core.ui.navigation.Routes
import com.panabuntu.tmdb.core.common.util.decodeAllStrings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.KType
import kotlin.reflect.typeOf

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

inline fun <reified T : Routes> SavedStateHandle.navArgs(
    typeMap: Map<KType, NavType<*>> = emptyMap()
): T {
    return toRoute<T>(typeMap).decodeAllStrings()
}

inline fun <reified T : Any> serializableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String) =
        bundle.getString(key)?.let<String, T>(json::decodeFromString)

    override fun parseValue(value: String): T = json.decodeFromString(value)

    override fun serializeAsValue(value: T): String = json.encodeToString(value)

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, json.encodeToString(value))
    }
}

inline fun <reified T : Any> navType(): Pair<KType, NavType<*>> {
    return typeOf<T>() to serializableType<T>()
}

fun setStatusBarLightStatusFromBackground(
    view: View,
    backgroundColor: Color
) {
    val isLightStatus = backgroundColor.getOnBackgroundColor() != Color.White

    (view.context as? Activity)?.run {
        WindowCompat.getInsetsController(
            window,
            view
        ).run {
            isAppearanceLightStatusBars = isLightStatus
        }
    }
}