package com.buntupana.tmdb.feature.discover.presentation.comp

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.theme.Dimens

private const val PLACE_HOLDER_ITEM_NUMBER = 6

@Composable
fun CarouselMediaItem(
    modifier: Modifier = Modifier,
    mediaItemList: List<MediaItem>,
    itemWidth: Dp = Dimens.carouselMediaItemWidth,
    paddingHorizontal: Dp = 12.dp,
    fontSize: TextUnit = TextUnit.Unspecified,
    lazyListState: LazyListState = rememberLazyListState(),
    onItemClicked: (mediaItem: MediaItem, mainPosterColor: Color) -> Unit
) {
    LazyRow(
        modifier = modifier.animateContentSize(),
        userScrollEnabled = mediaItemList.isNotEmpty(),
        state = lazyListState
    ) {
        if (mediaItemList.isEmpty()) {

            items(PLACE_HOLDER_ITEM_NUMBER) { index ->

                // Adding some padding at the start
                if (index == 0) {
                    Spacer(modifier = Modifier.width(paddingHorizontal))
                }

                MediaItemVerticalPlaceHolder(
                    modifier = Modifier.width(itemWidth),
                    fontSize = fontSize
                )

                // Adding some padding at end
                if (index == mediaItemList.size - 1) {
                    Spacer(modifier = Modifier.width(paddingHorizontal))
                }
            }

        } else {
            items(mediaItemList.size) { index ->

                // Adding some padding at the start
                if (index == 0) {
                    Spacer(modifier = Modifier.width(paddingHorizontal))
                }

                // Adding media item
                mediaItemList[index].let { mediaItem ->
                    MediaItemVertical(
                        modifier = Modifier
                            .width(itemWidth)
                            .clip(RoundedCornerShape(Dimens.posterRound)),
                        mediaItem = mediaItem,
                        fontSize = fontSize,
                        onClick = { mainPosterColor ->
                            onItemClicked(mediaItem, mainPosterColor)
                        }
                    )
                }

                // Adding some padding at end
                if (index == mediaItemList.size - 1) {
                    Spacer(modifier = Modifier.width(paddingHorizontal))
                }
            }
        }
    }
}