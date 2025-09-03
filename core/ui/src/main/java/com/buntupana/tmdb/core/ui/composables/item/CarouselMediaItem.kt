package com.buntupana.tmdb.core.ui.composables.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.ShowMoreButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.mediaItemMovie
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.util.Const.PLACE_HOLDER_ITEM_NUMBER
import com.panabuntu.tmdb.core.common.util.isNotNullOrEmpty
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun CarouselMediaItem(
    modifier: Modifier = Modifier,
    mediaItemList: List<MediaItem>?,
    animationEnabled: Boolean = false,
    isLoadingError: Boolean,
    itemWidth: Dp = Dimens.carouselMediaItemWidth,
    paddingHorizontal: Dp = 12.dp,
    fontSize: TextUnit = TextUnit.Unspecified,
    lazyListState: LazyListState = rememberLazyListState(),
    onItemClicked: (MediaItem, mainPosterColor: Color) -> Unit,
    onRetryClicked: () -> Unit,
    onShowMoreClick: (() -> Unit)? = null
) {

    Box(
        modifier = modifier,
    ) {

        LazyRow(
            userScrollEnabled = mediaItemList.isNotNullOrEmpty(),
            state = lazyListState,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (mediaItemList.isNullOrEmpty() || isLoadingError) {

                items(PLACE_HOLDER_ITEM_NUMBER) { index ->
                    // Adding some padding at the start
                    if (index == 0) {
                        Spacer(modifier = Modifier.width(paddingHorizontal))
                    }

                    MediaItemVerticalPlaceHolder(
                        modifier = Modifier.width(itemWidth),
                        fontSize = fontSize
                    )
                }
            } else {
                items(
                    count = mediaItemList.size,
                    key = { index -> if (animationEnabled) mediaItemList[index].id else index }
                ) { index ->

                    // Adding some padding at the start
                    if (index == 0) {
                        Spacer(modifier = Modifier.width(paddingHorizontal))
                    }

                    // Adding media item
                    mediaItemList[index].let { mediaItem ->

                        MediaItemVertical(
                            modifier = Modifier
                                .animateItem(),
                            width = itemWidth,
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

                if (mediaItemList.count() > PLACE_HOLDER_ITEM_NUMBER && onShowMoreClick != null) {

                    item {
                        Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))

                        ShowMoreButton(
                            modifier = Modifier.padding(bottom = itemWidth),
                            onClick = onShowMoreClick
                        )

                        Spacer(modifier = Modifier.padding(horizontal = Dimens.padding.tiny))
                    }
                }
            }
        }

        if (mediaItemList?.isEmpty() == true) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = Dimens.padding.huge),
                    text = stringResource(RCore.string.message_no_results_found),
                    fontWeight = FontWeight.Bold
                )
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
    AppTheme {
        CarouselMediaItem(
            mediaItemList = listOf(mediaItemMovie, mediaItemMovie.copy(id = 9)),
            isLoadingError = false,
            onItemClicked = { _, _ -> },
            onRetryClicked = {},
            onShowMoreClick = {}
        )
    }
}