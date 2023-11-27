package com.buntupana.tmdb.feature.detail.presentation.media.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.composables.ImageFromUrl
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.feature.detail.presentation.mediaItemMovie

@Composable
fun RecommendationItem(
    modifier: Modifier = Modifier,
    itemWidth: Dp,
    mediaItem: MediaItem,
    onItemClick: (mediaId: Long, mediaType: MediaType) -> Unit
) {
    Column(
        modifier = modifier
            .width(itemWidth)
            .padding(Dimens.padding.verticalItem)
            .clickable { onItemClick(mediaItem.id, mediaItem.mediaType) },
    ) {
        ImageFromUrl(
            modifier = Modifier
                .clip(RoundedCornerShape(Dimens.posterRound))
                .aspectRatio(Dimens.aspectRatioMediaRecommendation)
                .fillMaxWidth(),
            imageUrl = mediaItem.backdropUrl
        )
        Text(
            modifier = Modifier.padding(vertical = Dimens.padding.small),
            text = mediaItem.name,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview
@Composable
private fun RecommendationItemPreview() {
    RecommendationItem(
        itemWidth = Dimens.recommendationItemWidth,
        mediaItem = mediaItemMovie,
        onItemClick = { _, _ -> }
    )
}
