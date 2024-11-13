package com.buntupana.tmdb.feature.discover.presentation.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PlaceHolderColor
import com.buntupana.tmdb.core.ui.util.spToDp

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

            ConstraintLayout {
                val (posterImage, infoColumn, userScore) = createRefs()

                Box(modifier = Modifier
                    .clip(RoundedCornerShape(Dimens.posterRound))
                    .aspectRatio(2f / 3f)
                    .constrainAs(posterImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .background(PlaceHolderColor)
                )

                Box(modifier = Modifier
                    .size((36f * maxWidth.value / 120f).dp)
                    .constrainAs(userScore) {
                        start.linkTo(posterImage.start, 4.dp)
                        top.linkTo(posterImage.bottom)
                        bottom.linkTo(posterImage.bottom)
                    }) {

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
                        text = "Movie Title",
                        maxLines = MAX_TITLE_LINES,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = {
                            // Calc how many lines we need to fill the bottom
                            extraLinesCount = MAX_TITLE_LINES - it.lineCount
                        },
                        fontSize = fontSize,
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .background(PlaceHolderColor),
                        color = PlaceHolderColor
                    )
                    Text(
                        text = "",
                        fontWeight = FontWeight.Normal,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontSize = fontSize,
                        color = PlaceHolderColor
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
fun MediaItemPlaceHolderPreview() {
    MediaItemVerticalPlaceHolder(
        modifier = Modifier.width(120.dp)
    )
}