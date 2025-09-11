package com.buntupana.tmdb.feature.detail.presentation.media.comp

import android.content.res.Configuration
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsMovieSample
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank

@Composable
fun Header(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    mediaDetails: MediaDetails,
    backgroundColor: Color,
    setDominantColor: (dominantColor: Color) -> Unit
) {

    // If there is no image info, the header won't be displayed
    if (mediaDetails.backdropUrl.isNullOrBlank() && mediaDetails.posterUrl.isNullOrBlank()) {
        return
    }

    Box(
        modifier
            .fillMaxWidth()
            .aspectRatio(Dimens.aspectRatioMediaBackdrop),
    ) {

        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp),
                painter = ColorPainter(backgroundColor),
                contentDescription = null
            )

            Box {

                if (mediaDetails.backdropUrl.isNotNullOrBlank()) {
                    ImageFromUrl(
                        modifier = Modifier.fillMaxSize(),
                        imageUrl = mediaDetails.backdropUrl,
                        showPlaceHolder = false
                    )
                }
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
        if (mediaDetails.posterUrl.isNullOrBlank() && isLoading.not()) {
            return
        }

        // if no backdrop image, the poster image will be shown in the center
        val posterArrangement =
            if (mediaDetails.backdropUrl.isNullOrBlank()) Arrangement.Center else Arrangement.Start

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = posterArrangement
        ) {

            if (mediaDetails.backdropUrl.isNotNullOrBlank()) {
                Spacer(modifier = Modifier.width(Dimens.padding.medium))
            }
            ImageFromUrl(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .clip(RoundedCornerShape(Dimens.posterRound))
                    .aspectRatio(Dimens.aspectRatioMediaPoster),
                imageUrl = mediaDetails.posterUrl,
                setDominantColor = { setDominantColor(it) },
                showPlaceHolder = false
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true,
)
@Composable
private fun HeaderPreview() {
    AppTheme {
        Header(
            modifier = Modifier,
            isLoading = false,
            mediaDetails = mediaDetailsMovieSample,
            backgroundColor = MaterialTheme.colorScheme.surfaceDim,
            setDominantColor = {}
        )
    }
}