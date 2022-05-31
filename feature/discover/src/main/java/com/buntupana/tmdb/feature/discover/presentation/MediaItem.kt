package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.buntupana.tmdb.core.presentation.UserScore
import com.buntupana.tmdb.core.presentation.theme.HkFontFamily
import com.buntupana.tmdb.feature.discover.domain.model.MovieItem

@Composable
fun MediaItem(
    modifier: Modifier = Modifier,
    movie: MovieItem
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
                    model = movie.posterPath,
                    contentDescription = null
                )
                UserScore(
                    score = movie.voteAverage,
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

                    Text(
                        text = movie.title.addEmptyLines(3),
                        maxLines = 3,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        movie.releaseDate,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.alpha(0.6f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

fun String.addEmptyLines(lines: Int) = this + "\n".repeat(lines)

@Preview(showBackground = true)
@Composable
fun MediaItemPreview() {
    MediaItem(
        modifier = Modifier.width(120.dp),
        movie = MovieItem(
            0,
            "Fantastics Beast: The Secrets of Dumbledore",
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