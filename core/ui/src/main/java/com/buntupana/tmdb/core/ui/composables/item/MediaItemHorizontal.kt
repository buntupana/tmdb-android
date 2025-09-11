package com.buntupana.tmdb.core.ui.composables.item

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
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
import com.buntupana.tmdb.core.ui.composables.AppCard
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
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

    var mainPosterColor: Color = MaterialTheme.colorScheme.surfaceDim

    AppCard(
        modifier = modifier
            .height(height)
            .padding(
                horizontal = Dimens.padding.horizontal,
                vertical = Dimens.padding.verticalItem
            ),
        onClick = { onMediaClick(mediaId, mainPosterColor) }
    ) {
        Row(
            modifier = modifier
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
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Composable
private fun MediaItemHorizontalPreview() {
    AppTheme {
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
}

@Composable
fun MediaItemHorizontalPlaceHolder(
    modifier: Modifier = Modifier,
    height: Dp = Dimens.imageSize.posterHeight,
) {

    val placeHolderColor = MaterialTheme.colorScheme.surfaceContainerHigh

    AppCard(
        modifier = modifier
            .height(height)
            .padding(
                horizontal = Dimens.padding.horizontal,
                vertical = Dimens.padding.verticalItem
            )
    ) {
        Row {

            Box(
                modifier = Modifier
                    .aspectRatio(2f / 3f)
                    .background(placeHolderColor)
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
                        .background(placeHolderColor)
                        .padding(4.dp)
                        .fillMaxWidth(),
                    text = "",
                    minLines = 1,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    color = placeHolderColor
                )

                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(placeHolderColor)
                        .padding(4.dp)
                        .fillMaxWidth(),
                    text = "",
                    minLines = 2,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    color = placeHolderColor
                )
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Composable
private fun MediaItemHorizontalPlaceHolderPreview() {
    AppTheme {
        MediaItemHorizontalPlaceHolder(
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}