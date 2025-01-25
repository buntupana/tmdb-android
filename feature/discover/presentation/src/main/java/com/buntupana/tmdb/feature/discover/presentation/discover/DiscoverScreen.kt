package com.buntupana.tmdb.feature.discover.presentation.discover

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.ui.composables.CarouselMediaItem
import com.buntupana.tmdb.core.ui.composables.TitleAndFilter
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.discover.presentation.R
import com.buntupana.tmdb.feature.discover.presentation.filter_type.PopularFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.TrendingFilter
import com.panabuntu.tmdb.core.common.entity.MediaType

@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = hiltViewModel(),
    onMediaItemClicked: (mediaItemId: Long, mediaItemType: MediaType, posterDominantColor: Color) -> Unit
) {

    DiscoverContent(
        state = viewModel.state,
        changeTrendingType = { trendingType ->
            viewModel.onEvent(DiscoverEvent.ChangeTrendingType(trendingType))
        },
        changePopularType = { popularFilter ->
            viewModel.onEvent(DiscoverEvent.ChangePopularType(popularFilter))
        },
        changeFreeToWatchType = { freeToWatchType ->
            viewModel.onEvent(DiscoverEvent.ChangeFreeToWatchType(freeToWatchType))
        },
        navigateToDetail = { mediaItem, posterDominantColor ->
            when (mediaItem) {
                is com.panabuntu.tmdb.core.common.model.MediaItem.Movie -> {
                    onMediaItemClicked(
                        mediaItem.id,
                        MediaType.MOVIE,
                        posterDominantColor
                    )
                }

                is com.panabuntu.tmdb.core.common.model.MediaItem.TvShow -> {
                    onMediaItemClicked(
                        mediaItem.id,
                        MediaType.TV_SHOW,
                        posterDominantColor
                    )
                }
            }
        }
    )
}

@Composable
fun DiscoverContent(
    state: DiscoverState,
    changeTrendingType: (trendingFilter: TrendingFilter) -> Unit,
    changePopularType: (popularFilter: PopularFilter) -> Unit,
    changeFreeToWatchType: (freeToWatchFilter: MediaFilter) -> Unit,
    navigateToDetail: (mediaItem: com.panabuntu.tmdb.core.common.model.MediaItem, posterDominantColor: Color) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {

        val lazyListStateTrending: LazyListState = rememberLazyListState()
        val lazyListStatePopular: LazyListState = rememberLazyListState()
        val lazyListStateFree: LazyListState = rememberLazyListState()

        TitleAndFilter(
            modifier = Modifier.padding(vertical = Dimens.padding.medium),
            title = stringResource(id = R.string.text_trending),
            filterSet = state.trendingFilterSet,
            indexSelected = state.trendingFilterSet.indexOf(state.trendingFilterSelected),
            filterClicked = { item, _ ->
                changeTrendingType(item)
                lazyListStateTrending.requestScrollToItem(index = 0)
            }
        )
        Box(modifier = Modifier.fillMaxWidth()) {

            Image(
                painter = painterResource(id = R.drawable.img_trending),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )

            CarouselMediaItem(
                modifier = Modifier.fillMaxWidth(),
                mediaItemList = state.trendingMediaItemList,
                isLoadingError = state.isTrendingMediaLoadingError,
                lazyListState = lazyListStateTrending,
                onItemClicked = { mediaItem, mainPosterColor ->
                    navigateToDetail(mediaItem, mainPosterColor)
                },
                onRetryClicked = {
                    changeTrendingType(state.trendingFilterSelected)
                }
            )
        }

        TitleAndFilter(
            modifier = Modifier.padding(vertical = Dimens.padding.medium),
            title = stringResource(id = R.string.text_whats_popular),
            filterSet = state.popularFilterSet,
            indexSelected = state.popularFilterSet.indexOf(state.popularFilterSelected),
            filterClicked = { item, _ ->
                changePopularType(item)
                lazyListStatePopular.requestScrollToItem(index = 0)
            }
        )

        CarouselMediaItem(
            modifier = Modifier.fillMaxWidth(),
            mediaItemList = state.popularMediaItemList,
            isLoadingError = state.isPopularMediaLoadingError,
            lazyListState = lazyListStatePopular,
            onItemClicked = { mediaItem, mainPosterColor ->
                navigateToDetail(mediaItem, mainPosterColor)
            },
            onRetryClicked = {
                changePopularType(state.popularFilterSelected)
            }
        )

        TitleAndFilter(
            modifier = Modifier.padding(vertical = Dimens.padding.medium),
            title = stringResource(id = R.string.text_free_to_watch),
            filterSet = state.freeToWatchFilterSet,
            indexSelected = state.freeToWatchFilterSet.indexOf(state.freeToWatchFilterSelected),
            filterClicked = { item, _ ->
                changeFreeToWatchType(item)
                lazyListStateFree.requestScrollToItem(index = 0)
            }
        )

        CarouselMediaItem(
            modifier = Modifier.fillMaxWidth(),
            mediaItemList = state.freeToWatchMediaItemList,
            isLoadingError = state.isFreeToWatchMediaLoadingError,
            lazyListState = lazyListStateFree,
            onItemClicked = { mediaItem, mainPosterColor ->
                navigateToDetail(mediaItem, mainPosterColor)
            },
            onRetryClicked = {
                changeFreeToWatchType(state.freeToWatchFilterSelected)
            }
        )

        Spacer(modifier = Modifier.padding(vertical = Dimens.padding.medium))
    }
}

@Preview(showBackground = true)
@Composable
fun DiscoverScreenPreview() {
    DiscoverContent(
        state = DiscoverState(),
        changeTrendingType = {},
        changePopularType = {},
        changeFreeToWatchType = {},
        navigateToDetail = { _, _ -> }
    )
}

