package com.buntupana.tmdb.core.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.request.crossfade
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.PlaceHolderColor
import com.buntupana.tmdb.core.ui.util.getDominantColor
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank

@Composable
fun ImageFromUrl(
    modifier: Modifier,
    imageUrl: String?,
    contentDescription: String? = null,
    crossFade: Boolean = true,
    contentScale: ContentScale = ContentScale.Crop,
    placeHolderColor: Color = PlaceHolderColor,
    showPlaceHolder: Boolean = true,
    setDominantColor: ((dominantColor: Color) -> Unit)? = null
) {

    if (imageUrl.isNotNullOrBlank()) {

        Box(
            modifier = modifier
        ) {

            if (showPlaceHolder) {
                Box(modifier = modifier.background(placeHolderColor))
            }

            val allowHardware = setDominantColor == null

            AsyncImage(
                modifier = modifier,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(crossFade)
                    .allowHardware(allowHardware)
                    .listener { _, result ->
                        if (setDominantColor == null) {
                            return@listener
                        }
                        result.image.getDominantColor { dominantColor ->
                            setDominantColor(dominantColor)
                        }
                    }
                    .build(),
                contentScale = contentScale,
                contentDescription = contentDescription
            )
        }
    } else {
        Surface(
            modifier = modifier,
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(PlaceHolderColor),
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
    ImageFromUrl(modifier = Modifier.size(100.dp), imageUrl = null)
}