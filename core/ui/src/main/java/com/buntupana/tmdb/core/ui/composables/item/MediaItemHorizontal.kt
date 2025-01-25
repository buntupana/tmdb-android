package com.buntupana.tmdb.core.ui.composables.item

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.Dimens


@Composable
fun MediaItemHorizontal(
    modifier: Modifier = Modifier,
    onMediaClick: ((mediaId: Long, mainPosterColor: Color) -> Unit),
    mediaId: Long,
    title: String,
    posterUrl: String?,
    overview: String,
    releaseDate: String
) {
    Surface(
        modifier = modifier
            .padding(
                horizontal = Dimens.padding.horizontal,
                vertical = Dimens.padding.verticalItem
            ),
        shape = RoundedCornerShape(Dimens.posterRound),
        shadowElevation = Dimens.cardElevation
    ) {
        MediaItemHorizontalBase(
            modifier = modifier,
            onMediaClick = onMediaClick,
            mediaId = mediaId,
            title = title,
            posterUrl = posterUrl,
            overview = overview,
            releaseDate = releaseDate
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaItemHorizontalPreview() {
    MediaItemHorizontal(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.imageSize.posterHeight),
        mediaId = 0L,
        title = "Thor: Love and Thunder",
        posterUrl = null,
        overview = "After his retirement is interrupted by Gorr the God Butcher, a galactic killer who seeks the extinction of the gods, Thor enlists the help of King",
        releaseDate = "10-11-20",
        onMediaClick = { _, _ -> }
    )
}