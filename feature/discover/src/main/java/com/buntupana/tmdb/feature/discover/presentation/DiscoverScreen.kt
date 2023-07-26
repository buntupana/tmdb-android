package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.theme.PrimaryColor
import com.buntupana.tmdb.feature.discover.R
import com.buntupana.tmdb.feature.discover.presentation.comp.CarouselMediaItem
import com.buntupana.tmdb.feature.discover.presentation.comp.TitleAndFilter
import com.buntupana.tmdb.feature.discover.presentation.comp.TopBar
import com.buntupana.tmdb.feature.discover.presentation.filter_type.FreeToWatchFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.PopularFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.TrendingFilter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = hiltViewModel(),
    discoverNavigator: DiscoverNavigator
) {

    DiscoverContent(
        state = viewModel.state,
        navigateToSearch = { discoverNavigator.navigateToSearch() },
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
                is MediaItem.Movie -> {
                    discoverNavigator.navigateToMediaDetail(
                        mediaItem.id,
                        MediaType.MOVIE,
                        posterDominantColor
                    )
                }

                is MediaItem.TvShow -> {
                    discoverNavigator.navigateToMediaDetail(
                        mediaItem.id,
                        MediaType.TV_SHOW,
                        posterDominantColor
                    )
                }

                is MediaItem.Person -> {}
                MediaItem.Unknown -> {}
            }
        }
    )
}


@Composable
fun DiscoverContent(
    state: DiscoverState,
    navigateToSearch: () -> Unit,
    changeTrendingType: (trendingFilter: TrendingFilter) -> Unit,
    changePopularType: (popularFilter: PopularFilter) -> Unit,
    changeFreeToWatchType: (freeToWatchFilter: FreeToWatchFilter) -> Unit,
    navigateToDetail: (mediaItem: MediaItem, posterDominantColor: Color) -> Unit
) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(color = PrimaryColor)
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = scrollState
            )
    ) {

        TopBar(
            clickOnSearch = {
                navigateToSearch()
            }
        )

        TitleAndFilter(
            title = stringResource(id = R.string.text_trending),
            filterSet = state.trendingFilterSet,
            indexSelected = state.trendingFilterSet.indexOf(state.trendingFilterSelected),
            filterClicked = { item, index ->
                changeTrendingType(item)
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
                state.trendingMediaItemList,
                onItemClicked = { mediaItem, mainPosterColor ->
                    navigateToDetail(mediaItem, mainPosterColor)
                }
            )
        }

        TitleAndFilter(
            title = stringResource(id = R.string.text_whats_popular),
            filterSet = state.popularFilterSet,
            indexSelected = state.popularFilterSet.indexOf(state.popularFilterSelected),
            filterClicked = { item, index ->
                changePopularType(item)
            }
        )
        CarouselMediaItem(
            modifier = Modifier.fillMaxWidth(),
            state.popularMediaItemList,
            onItemClicked = { mediaItem, mainPosterColor ->
                navigateToDetail(mediaItem, mainPosterColor)
            }
        )

        TitleAndFilter(
            title = stringResource(id = R.string.text_free_to_watch),
            filterSet = state.freeToWatchFilterSet,
            indexSelected = state.freeToWatchFilterSet.indexOf(state.freeToWatchFilterSelected),
            filterClicked = { item, index ->
                changeFreeToWatchType(item)
            }
        )
        CarouselMediaItem(
            modifier = Modifier.fillMaxWidth(),
            state.freeToWatchMediaItemList,
            onItemClicked = { mediaItem, mainPosterColor ->
                navigateToDetail(mediaItem, mainPosterColor)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DiscoverScreenPreview() {
    DiscoverContent(
        state = DiscoverState(),
        navigateToSearch = { /*TODO*/ },
        changeTrendingType = {},
        changePopularType = {},
        changeFreeToWatchType = {},
        navigateToDetail = { _, _ -> }
    )
}

