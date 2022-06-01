package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.theme.Dimens

@Composable
fun CarouselMediaItem(
    modifier: Modifier = Modifier,
    mediaItemList: List<MediaItem>,
    itemWidth: Dp = Dimens.carouselMediaItemWidth,
    paddingHorizontal: Dp = 12.dp,
    onItemClicked: (MediaItem) -> Unit
) {
    LazyRow(
        modifier = modifier
    ) {
        items(mediaItemList.size) { i ->

            // Adding some padding at the start
            if (i == 0) {
                Spacer(modifier = Modifier.width(paddingHorizontal))
            }

            // Adding media item
            mediaItemList[i].let { mediaItem ->
                MediaItemVertical(
                    modifier = Modifier
                        .width(itemWidth)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            onItemClicked(mediaItem)
                        },
                    mediaItem = mediaItem
                )
            }

            // Adding some padding at end
            if (i == mediaItemList.size - 1) {
                Spacer(modifier = Modifier.width(paddingHorizontal))
            }
        }
    }
}