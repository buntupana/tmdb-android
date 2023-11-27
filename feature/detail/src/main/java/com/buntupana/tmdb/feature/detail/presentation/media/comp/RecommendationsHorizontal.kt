package com.buntupana.tmdb.feature.detail.presentation.media.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.feature.detail.presentation.mediaDetailsMovieSample

@Composable
fun RecommendationsHorizontal(
    modifier: Modifier = Modifier,
    mediaItemList: List<MediaItem>,
    onItemClick: (mediaId: Long, mediaType: MediaType) -> Unit
) {

    if (mediaItemList.isEmpty()) {
        return
    }

    Column(
        modifier = modifier
    ) {
        Divider()

        Text(
            modifier = Modifier.padding(
                horizontal = Dimens.padding.horizontal,
                vertical = Dimens.padding.vertical
            ),
            text = "Recommendations",
            fontSize = Dimens.textSize.title,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))
            }
            items(mediaItemList) { item ->
                RecommendationItem(
                    itemWidth = Dimens.recommendationItemWidth,
                    mediaItem = item,
                    onItemClick = onItemClick
                )
            }
            item {
                Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))
            }
        }
    }
}

@Preview
@Composable
private fun RecommendationsHorizontalPreview() {
    RecommendationsHorizontal(
        modifier = Modifier.fillMaxWidth(),
        mediaItemList = mediaDetailsMovieSample.recommendationList,
        onItemClick = {_, _ ->}
    )
}