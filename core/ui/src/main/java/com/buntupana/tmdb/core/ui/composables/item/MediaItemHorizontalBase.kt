package com.buntupana.tmdb.core.ui.composables.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.ui.theme.Dimens


@Composable
fun MediaItemHorizontalBase(
    modifier: Modifier = Modifier,
    onMediaClick: ((mediaId: Long, mainPosterColor: Color) -> Unit),
    mediaId: Long,
    title: String,
    posterUrl: String?,
    overview: String,
    releaseDate: String
) {

    var mainPosterColor: Color = DetailBackgroundColor

    Row(
        modifier = modifier
            .clickable { onMediaClick(mediaId, mainPosterColor) }
    ) {

        ImageFromUrl(
            modifier = Modifier
                .aspectRatio(2f / 3f),
            imageUrl = posterUrl,
            setDominantColor = { mainPosterColor = it }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.padding.small),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (releaseDate.isNotBlank()) {
                Text(
                    modifier = Modifier.alpha(0.7f),
                    text = releaseDate,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (overview.isNotBlank()) {
                Spacer(modifier = Modifier.height(Dimens.padding.small))
                Text(
                    text = overview,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaItemHorizontalBasePreview() {
    MediaItemHorizontalBase(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.imageSize.posterHeight),
        mediaId = 0L,
        title = "Thor: Love and Thunder",
        posterUrl = null,
        overview = "After his retirement is interrupted by Gorr the God Butcher, a galactic killer who seeks the extinction of the gods, Thor enlists the help of King",
        releaseDate = "10-11-20",
        onMediaClick = { _, _ -> }
    )
}