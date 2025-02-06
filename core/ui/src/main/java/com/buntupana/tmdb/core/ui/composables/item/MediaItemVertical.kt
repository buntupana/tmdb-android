package com.buntupana.tmdb.core.ui.composables.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.composables.widget.UserScore
import com.buntupana.tmdb.core.ui.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.HkFontFamily
import com.buntupana.tmdb.core.ui.util.isInvisible
import com.buntupana.tmdb.core.ui.util.mediaItemMovie
import com.panabuntu.tmdb.core.common.model.MediaItem

private const val MAX_TITLE_LINES = 3

@Composable
fun MediaItemVertical(
    modifier: Modifier = Modifier,
    mediaItem: MediaItem,
    fontSize: TextUnit = TextUnit.Unspecified,
    onClick: (mainPosterColor: Color) -> Unit
) {

    var mainPosterColor: Color = DetailBackgroundColor

    BoxWithConstraints(
        modifier = modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(Dimens.posterRound))
            .clickable {
                onClick(mainPosterColor)
            }
    ) {

        val maxWidth = maxWidth

        Column {
            Box {
                val userScoreSize = (36f * maxWidth.value / 120f).dp

                ImageFromUrl(
                    modifier = Modifier
                        .padding(bottom = userScoreSize / 2)
                        .clip(RoundedCornerShape(Dimens.posterRound))
                        .aspectRatio(Dimens.aspectRatioMediaPoster),
                    imageUrl = mediaItem.posterUrl,
                    showPlaceHolder = true,
                    setDominantColor = { dominantColor ->
                        mainPosterColor = dominantColor
                    }
                )

                Box(
                    modifier = Modifier
                        .padding(start = Dimens.padding.tiny)
                        .align(Alignment.BottomStart)
                        .size(userScoreSize)
                ) {
                    UserScore(
                        score = mediaItem.voteAverage,
                        modifier = Modifier.fillMaxSize(),
                        fontFamily = HkFontFamily
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
            ) {

                Column(
                    modifier = Modifier.isInvisible(true)
                ) {

                    Text(
                        text = "This a dummy text just for draw max size of this composable, it has be long to cover the min 3 lines",
                        maxLines = MAX_TITLE_LINES,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = fontSize
                    )

                    Text(
                        text = "This a dummy text just for draw max size of this composable, it has be long to cover the min 3 lines",
                        fontWeight = FontWeight.Normal,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontSize = fontSize
                    )
                }

                Column {

                    Text(
                        text = mediaItem.name,
                        maxLines = MAX_TITLE_LINES,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = fontSize
                    )

                    Text(
                        text = mediaItem.releaseDate,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.alpha(0.6f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontSize = fontSize
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaItemPreview() {
    MediaItemVertical(
        modifier = Modifier.width(Dimens.carouselMediaItemWidth),
        mediaItem = mediaItemMovie,
        onClick = { }
    )
}