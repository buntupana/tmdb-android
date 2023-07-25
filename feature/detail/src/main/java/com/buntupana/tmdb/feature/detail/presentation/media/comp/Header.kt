package com.buntupana.tmdb.feature.detail.presentation.media.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.util.getDominantColor
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsMovieSample
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun Header(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    mediaDetails: MediaDetails,
    backgroundColor: Color,
    setDominantColor: (dominantColor: Color) -> Unit
) {

    // If there is no image info, the header won't be displayed
    if (mediaDetails.backdropUrl.isBlank() && mediaDetails.posterUrl.isBlank()) {
        return
    }

    Box(
        modifier
            .fillMaxWidth()
            .height(200.dp),
    ) {

        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(70.dp),
                painter = ColorPainter(backgroundColor),
                contentDescription = null
            )

            Box {

                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .placeholder(
                            visible = isLoading,
                            highlight = PlaceholderHighlight.fade()
                        ),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(mediaDetails.backdropUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(80.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(backgroundColor, Color.Transparent)
                            )
                        ),
                )
            }
        }

        // hiding poster image when there is no info
        if (mediaDetails.posterUrl.isBlank() && isLoading.not()) {
            return
        }

        // if no backdrop image, the poster image will be shown in the center
        val posterArrangement =
            if (mediaDetails.backdropUrl.isBlank()) Arrangement.Center else Arrangement.Start

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = posterArrangement
        ) {

            if (mediaDetails.backdropUrl.isNotBlank()) {
                Spacer(modifier = Modifier.width(Dimens.padding.medium))
            }
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .clip(RoundedCornerShape(Dimens.posterRound))
                    .aspectRatio(2f / 3f)
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.fade()
                    ),
                alignment = Alignment.CenterStart,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mediaDetails.posterUrl)
                    .crossfade(true)
                    .allowHardware(false)
                    .listener { request, result ->
                        result.drawable.getDominantColor { dominantColor ->
                            setDominantColor(dominantColor)
                        }
                    }
                    .build(),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun HeaderPreview() {

    Header(
        isLoading = false,
        mediaDetails = mediaDetailsMovieSample,
        backgroundColor = DetailBackgroundColor,
        setDominantColor = {}
    )
}