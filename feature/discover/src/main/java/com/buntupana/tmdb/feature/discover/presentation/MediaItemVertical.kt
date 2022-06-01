package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.UserScore
import com.buntupana.tmdb.core.presentation.spToDp
import com.buntupana.tmdb.core.presentation.theme.HkFontFamily

private const val MAX_TITLE_LINES = 3

@Composable
fun MediaItemVertical(
    modifier: Modifier = Modifier,
    mediaItem: MediaItem
) {

    BoxWithConstraints(
        modifier = modifier
            .padding(4.dp)
    ) {

        Column {

            ConstraintLayout {
                val (posterImage, infoColumn, userScore) = createRefs()

                AsyncImage(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .aspectRatio(2f / 3f)
                        .constrainAs(posterImage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    model = mediaItem.posterPath,
                    contentDescription = null
                )
                UserScore(
                    score = mediaItem.voteAverage,
                    modifier = Modifier
                        .size(36.dp)
                        .constrainAs(userScore) {
                            start.linkTo(posterImage.start, 4.dp)
                            top.linkTo(posterImage.bottom)
                            bottom.linkTo(posterImage.bottom)
                        },
                    fontFamily = HkFontFamily
                )
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
                        mutableStateOf(0)
                    }

                    Text(
                        text = mediaItem.name,
                        maxLines = MAX_TITLE_LINES,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = {
                            // Calc how many lines we need to fill the bottom
                            extraLinesCount = MAX_TITLE_LINES - it.lineCount
                        }
                    )
                    Text(
                        text = mediaItem.releaseDate,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.alpha(0.6f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
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
            0,
            "Fantastics Beasts",
            "",
            "",
            "",
            "",
            "",
            emptyList(),
            0.0,
            67,
            0,
            "31-Dec-2018",
            false,
            false
        )
    )
}