package com.buntupana.tmdb.feature.discover.presentation.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.constraintlayout.compose.ConstraintLayout
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.composables.widget.UserScore
import com.buntupana.tmdb.core.ui.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.HkFontFamily
import com.buntupana.tmdb.core.ui.util.spToDp
import com.panabuntu.tmdb.core.common.model.MediaItem

private const val MAX_TITLE_LINES = 3

@Composable
fun MediaItemVertical(
    modifier: Modifier = Modifier,
    mediaItem: MediaItem,
    fontSize: TextUnit = TextUnit.Unspecified,
    onClick: (mainPosterColor: Color) -> Unit
) {

    BoxWithConstraints(
        modifier = modifier.padding(4.dp)
    ) {

        var mainPosterColor: Color = DetailBackgroundColor

        val maxWidth = maxWidth

        val voteAverage: Int?
        val releaseDate: String

        when (mediaItem) {
            is MediaItem.Movie -> {
                voteAverage = mediaItem.voteAverage
                releaseDate = mediaItem.releaseDate
            }

            is MediaItem.TvShow -> {
                voteAverage = mediaItem.voteAverage
                releaseDate = mediaItem.releaseDate
            }

            else -> {
                voteAverage = -1
                releaseDate = ""
            }
        }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(Dimens.posterRound))
                .clickable {
                    onClick(mainPosterColor)
                }
        ) {

            ConstraintLayout {
                val (posterImage, infoColumn, userScore) = createRefs()

                ImageFromUrl(
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimens.posterRound))
                        .aspectRatio(Dimens.aspectRatioMediaPoster)
                        .constrainAs(posterImage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    imageUrl = mediaItem.posterUrl,
                    setDominantColor = { mainPosterColor = it }
                )
                Box(
                    modifier = Modifier
                        .size((36f * maxWidth.value / 120f).dp)
                        .constrainAs(userScore) {
                            start.linkTo(posterImage.start, 4.dp)
                            top.linkTo(posterImage.bottom)
                            bottom.linkTo(posterImage.bottom)
                        }
                ) {
                    UserScore(
                        score = voteAverage,
                        modifier = Modifier.fillMaxSize(),
                        fontFamily = HkFontFamily
                    )
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .constrainAs(infoColumn) {
                            top.linkTo(userScore.bottom)
                            start.linkTo(posterImage.start)
                            end.linkTo(posterImage.end)
                        }) {

                    var extraLinesCount by remember {
                        mutableIntStateOf(0)
                    }

                    Text(
                        text = mediaItem.name,
                        maxLines = MAX_TITLE_LINES,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = {
                            // Calc how many lines we need to fill the bottom
                            extraLinesCount = MAX_TITLE_LINES - it.lineCount
                        },
                        fontSize = fontSize
                    )

                    Text(
                        text = releaseDate,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.alpha(0.6f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontSize = fontSize
                    )
                    // Height we need to fill the view
                    val lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * extraLinesCount
                    Spacer(
                        modifier = Modifier.height(spToDp(lineHeight))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MediaItemPreview() {
    MediaItemVertical(
        modifier = Modifier.width(120.dp),
        mediaItem = MediaItem.Movie(
            id = 0,
            name = "Fantastics Beasts: the Secrets of Dumbelbore",
            originalName = "",
            overview = "",
            posterUrl = "",
            backdropUrl = "",
            originalLanguage = "",
            genreIds = emptyList(),
            popularity = 0f,
            voteAverage = 67,
            voteCount = 0,
            releaseDate = "31-Dec-2018",
            video = false,
            adult = false
        ),
        onClick = { }
    )
}