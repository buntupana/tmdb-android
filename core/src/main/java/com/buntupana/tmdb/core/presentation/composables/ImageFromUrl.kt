package com.buntupana.tmdb.core.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.buntupana.tmdb.core.presentation.util.getDominantColor

@Composable
fun ImageFromUrl(
    modifier: Modifier,
    imageUrl: String,
    contentDescription: String? = null,
    crossFade: Boolean = true,
    contentScale: ContentScale = ContentScale.Crop,
    setDominantColor: ((dominantColor: Color) -> Unit)? = null
) {

    val allowHardware = setDominantColor == null

    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(crossFade)
            .allowHardware(allowHardware)
            .listener { request, result ->
                if (setDominantColor == null) {
                    return@listener
                }
                result.drawable.getDominantColor { dominantColor ->
                    setDominantColor(dominantColor)
                }
            }
            .build(),
        contentScale = contentScale,
        contentDescription = contentDescription
    )
}