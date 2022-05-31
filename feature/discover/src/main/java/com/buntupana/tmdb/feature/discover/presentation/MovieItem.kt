package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.buntupana.tmdb.core.presentation.UserScore
import com.buntupana.tmdb.core.presentation.theme.HkFontFamily
import com.buntupana.tmdb.feature.discover.domain.model.MovieItem

@Composable
fun MovieItem(
    movie: MovieItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .fillMaxHeight()
    ) {

        ConstraintLayout {

            val (posterImage, infoColumn, valorationText) = createRefs()

            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .aspectRatio(2f / 3f)
                    .height(0.dp)
                    .width(0.dp)
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
                    .constrainAs(valorationText) {
                        start.linkTo(posterImage.start, 8.dp)
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
                        top.linkTo(valorationText.bottom)
                        start.linkTo(posterImage.start)
                        end.linkTo(posterImage.end)
                    }) {

                Text(
                    text = movie.title,
                    maxLines = 3,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    movie.releaseDate,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

fun String.addEmptyLines(lines: Int) = this + "\n".repeat(lines)