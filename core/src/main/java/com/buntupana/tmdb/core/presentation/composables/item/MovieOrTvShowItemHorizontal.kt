package com.buntupana.tmdb.core.presentation.composables.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.util.getDominantColor


@Composable
fun MovieOrTvShowItemHorizontal(
    modifier: Modifier,
    clickable: ((mainPosterColor: Color) -> Unit)? = null,
    title: String,
    posterUrl: String,
    overview: String,
    releaseDate: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(Dimens.posterRound),
        shadowElevation = Dimens.cardElevation
    ) {

        var mainPosterColor: Color = DetailBackgroundColor

        Row(
            modifier = Modifier
                .clickable {
                    clickable?.invoke(mainPosterColor)
                }
        ) {

            AsyncImage(
                modifier = Modifier
                    .aspectRatio(2f / 3f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(posterUrl)
                    .crossfade(true)
                    .listener { _, result ->
                        result.drawable.getDominantColor {
                            mainPosterColor = it
                        }
                    }
                    .build(),
                contentDescription = null
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
}