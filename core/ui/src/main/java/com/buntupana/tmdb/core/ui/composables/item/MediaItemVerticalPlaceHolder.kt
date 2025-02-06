package com.buntupana.tmdb.core.ui.composables.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PlaceHolderColor
import com.buntupana.tmdb.core.ui.util.isInvisible

private const val MAX_TITLE_LINES = 3

@Composable
fun MediaItemVerticalPlaceHolder(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified
) {

    BoxWithConstraints(
        modifier = modifier
            .padding(4.dp)
    ) {

        val maxWidth = maxWidth

        Column {

            val userScoreSize = (36f * maxWidth.value / 120f).dp

            Box(
                modifier = Modifier
                    .padding(bottom = userScoreSize / 2)
                    .clip(RoundedCornerShape(Dimens.posterRound))
                    .aspectRatio(2f / 3f)
                    .background(PlaceHolderColor)
            ) {
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

                Text(
                    text = "Movie Title",
                    maxLines = MAX_TITLE_LINES,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = fontSize,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(PlaceHolderColor),
                    color = PlaceHolderColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MediaItemPlaceHolderPreview() {
    MediaItemVerticalPlaceHolder(
        modifier = Modifier.width(120.dp)
    )
}