package com.buntupana.tmdb.core.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.R as RCore

private const val PLACE_HOLDER_ITEM_NUMBER = 6

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselMediaItem(
    modifier: Modifier = Modifier,
    mediaItemList: List<com.panabuntu.tmdb.core.common.model.MediaItem>,
    isLoadingError: Boolean,
    itemWidth: Dp = Dimens.carouselMediaItemWidth,
    paddingHorizontal: Dp = 12.dp,
    fontSize: TextUnit = TextUnit.Unspecified,
    lazyListState: LazyListState = rememberLazyListState(),
    onItemClicked: (mediaItem: com.panabuntu.tmdb.core.common.model.MediaItem, mainPosterColor: Color) -> Unit,
    onRetryClicked: () -> Unit
) {

    Box(
        modifier = modifier.animateContentSize(),
    ) {
        LazyRow(
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
                                .animateItem()
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
        if (isLoadingError) {
            ErrorAndRetry(
                modifier = Modifier
                    .matchParentSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
                textColor = MaterialTheme.colorScheme.onBackground,
                errorMessage = stringResource(RCore.string.message_loading_content_error),
                onRetryClick = onRetryClicked
            )
        }
    }
}

@Preview
@Composable
fun CarouselMediaItemPreview() {
    CarouselMediaItem(
        mediaItemList = emptyList(),
        isLoadingError = true,
        onItemClicked = { _, _ -> },
        onRetryClicked = {}
    )
}