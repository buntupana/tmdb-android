package com.buntupana.tmdb.core.ui.composables.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PlaceHolderColor
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank


@Composable
fun MediaItemHorizontal(
    modifier: Modifier = Modifier,
    height: Dp = Dimens.imageSize.posterHeight,
    onMediaClick: (mediaId: Long, mainPosterColor: Color) -> Unit,
    mediaId: Long,
    title: String,
    posterUrl: String?,
    overview: String?,
    releaseDate: String?
) {
    Surface(
        modifier = modifier
            .height(height)
            .padding(
                horizontal = Dimens.padding.horizontal,
                vertical = Dimens.padding.verticalItem
            ),
        shape = RoundedCornerShape(Dimens.posterRound),
        shadowElevation = Dimens.cardElevation
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

                if (releaseDate.isNotNullOrBlank()) {
                    Text(
                        modifier = Modifier.alpha(0.7f),
                        text = releaseDate,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (overview.isNotNullOrBlank()) {
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
}

@Preview(showBackground = true)
@Composable
private fun MediaItemHorizontalPreview() {
    MediaItemHorizontal(
        modifier = Modifier
            .fillMaxWidth(),
        mediaId = 0L,
        title = "Thor: Love and Thunder",
        posterUrl = null,
        overview = "After his retirement is interrupted by Gorr the God Butcher, a galactic killer who seeks the extinction of the gods, Thor enlists the help of King",
        releaseDate = "10-11-20",
        onMediaClick = { _, _ -> }
    )
}

@Composable
fun MediaItemHorizontalPlaceHolder(
    modifier: Modifier = Modifier,
    height: Dp = Dimens.imageSize.posterHeight,
) {
    Surface(
        modifier = modifier
            .height(height)
            .padding(
                horizontal = Dimens.padding.horizontal,
                vertical = Dimens.padding.verticalItem
            ),
        shape = RoundedCornerShape(Dimens.posterRound),
        shadowElevation = Dimens.cardElevation
    ) {
        Row {

            Box(
                modifier = Modifier
                    .aspectRatio(2f / 3f)
                    .background(PlaceHolderColor)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimens.padding.small),
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(PlaceHolderColor)
                        .padding(4.dp),
                    text = "Movie Title Example",
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    color = PlaceHolderColor
                )

                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(PlaceHolderColor)
                        .padding(4.dp),
                    text = "Movie Title Example asdf asdf asdf adsf  asdf adsf asdf asdf asdf ",
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    color = PlaceHolderColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaItemHorizontalPlaceHolderPreview() {
    MediaItemHorizontalPlaceHolder(
        modifier = Modifier
            .fillMaxWidth(),
    )
}