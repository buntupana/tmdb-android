package com.buntupana.tmdb.core.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.request.crossfade
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.util.getDominantColor
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank

@Composable
fun ImageFromUrl(
    modifier: Modifier,
    imageUrl: String?,
    contentDescription: String? = null,
    crossFade: Boolean = true,
    contentScale: ContentScale = ContentScale.Crop,
    placeHolderColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    showPlaceHolder: Boolean = true,
    loadingContent: (@Composable () -> Unit)? = null,
    setDominantColor: ((dominantColor: Color) -> Unit)? = null
) {

    if (imageUrl.isNotNullOrBlank()) {

        var isLoading by remember { mutableStateOf(true) }

        val context = LocalContext.current

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {

            if (showPlaceHolder) {
                Box(modifier = modifier.background(placeHolderColor))
            }

            if (isLoading) {
                loadingContent?.invoke()
            }

            val allowHardware = setDominantColor == null

            AsyncImage(
                modifier = modifier,
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(crossFade)
                    .allowHardware(allowHardware)
                    .listener { _, result ->
                        if (setDominantColor == null) {
                            return@listener
                        }
                        result.image.getDominantColor(context) { dominantColor ->
                            setDominantColor(dominantColor)
                        }
                    }
                    .build(),
                contentScale = contentScale,
                contentDescription = contentDescription,
                onState = { state ->
                    isLoading = state is AsyncImagePainter.State.Loading
                }
            )
        }
    } else {
        Surface(
            modifier = modifier,
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(placeHolderColor),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(0.8f),
                    painter = painterResource(id = R.drawable.ic_image_empty),
                    contentDescription = contentDescription
                )
            }
        }
    }
}

@Preview
@Composable
private fun ImageFromUrlPreview() {
    AppTheme {
        ImageFromUrl(modifier = Modifier.size(100.dp), imageUrl = null)
    }
}

@Preview
@Composable
private fun ImageFromUrlPreviewDark() {
    AppTheme(darkTheme = true) {
        ImageFromUrl(modifier = Modifier.size(100.dp), imageUrl = null)
    }
}