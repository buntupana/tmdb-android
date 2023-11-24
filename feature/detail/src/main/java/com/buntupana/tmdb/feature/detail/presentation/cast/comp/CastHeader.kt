package com.buntupana.tmdb.feature.detail.presentation.cast.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.presentation.composables.ImageFromUrl
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.theme.Typography
import com.buntupana.tmdb.core.presentation.util.getOnBackgroundColor
import com.buntupana.tmdb.core.presentation.util.isNotNullOrBlank

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CastHeader(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    posterUrl: String?,
    mediaName: String,
    releaseYear: String?,
    setDominantColor: (color: Color) -> Unit
) {
    Row(
        modifier = modifier
            .height(120.dp)
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = Dimens.padding.medium),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        val titleArrangement = if (posterUrl.isNotNullOrBlank()) {
            ImageFromUrl(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .clip(RoundedCornerShape(Dimens.posterRound))
                    .aspectRatio(Dimens.aspectRatioMediaPoster),
                imageUrl = posterUrl,
                setDominantColor = { setDominantColor(it) }
            )
            Spacer(modifier = Modifier.padding(Dimens.padding.small))
            Arrangement.Start
        } else {
            Arrangement.Center
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = titleArrangement
        ) {
            Text(
                text = mediaName,
                color = backgroundColor.getOnBackgroundColor(),
                fontSize = Typography.titleLarge.fontSize,
                fontWeight = Typography.titleLarge.fontWeight
            )
            Spacer(modifier = Modifier.padding(Dimens.padding.tiny))
            if (releaseYear != null) {
                Text(
                    text = "($releaseYear)",
                    color = backgroundColor.getOnBackgroundColor(),
                    fontSize = Typography.titleLarge.fontSize,
                )
            }
        }
    }
}

@Preview
@Composable
private fun CastHeaderPreview() {
    CastHeader(
        backgroundColor = DetailBackgroundColor,
        posterUrl = null,
        mediaName = "Pain Hustlers",
        releaseYear = "2023",
        setDominantColor = {}
    )
}

