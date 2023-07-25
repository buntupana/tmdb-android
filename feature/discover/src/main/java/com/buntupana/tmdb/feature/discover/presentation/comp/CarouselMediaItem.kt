package com.buntupana.tmdb.feature.discover.presentation.comp

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.theme.Dimens
import kotlinx.coroutines.launch

private const val PLACE_HOLDER_ITEM_NUMBER = 6

@Composable
fun CarouselMediaItem(
    modifier: Modifier = Modifier,
    mediaItemList: List<MediaItem>,
    itemWidth: Dp = Dimens.carouselMediaItemWidth,
    paddingHorizontal: Dp = 12.dp,
    fontSize: TextUnit = TextUnit.Unspecified,
    onItemClicked: (mediaItem: MediaItem, mainPosterColor: Color) -> Unit
) {

    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LaunchedEffect(mediaItemList) {
        scope.launch {
//            listState.scrollToItem(0)
        }
    }

    LazyRow(
        modifier = modifier,
        userScrollEnabled = mediaItemList.isNotEmpty(),
        state = listState
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