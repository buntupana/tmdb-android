package com.buntupana.tmdb.feature.account.presentation.account.watchlist_favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.OrderButtonAnimation
import com.buntupana.tmdb.core.ui.composables.TopBarTitle
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.util.setStatusNavigationBarColor
import com.buntupana.tmdb.feature.account.presentation.R
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.launch
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun WatchlistScreen(
    viewModel: WatchlistFavoritesViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMediaClick: (mediaItemId: Long, mediaType: MediaType, mainPosterColor: Color?) -> Unit,
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    val movieItems = viewModel.state.movieItems?.collectAsLazyPagingItems()
    val tvShowItems = viewModel.state.tvShowItems?.collectAsLazyPagingItems()

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

            viewModel.onEvent(WatchlistFavoritesEvent.GetMediaItemList)

            launch {
                viewModel.sideEffect.collect { sideEffect ->
                    when (sideEffect) {
                        WatchlistFavoritesSideEffect.RefreshMediaItemList -> {
                            movieItems?.refresh()
                            tvShowItems?.refresh()
                        }
                    }
                }
            }
        }
    }

    WatchlistContent(
        state = viewModel.state,
        onBackClick = onBackClick,
        onSearchClick = onSearchClick,
        onMediaClick = { mediaItem, mainPosterColor ->
            onMediaClick(mediaItem.id, mediaItem.mediaType, mainPosterColor)
        },
        onOrderClick = {
            viewModel.onEvent(WatchlistFavoritesEvent.ChangeOrder)
        },
        onRetryClick = {
            viewModel.onEvent(WatchlistFavoritesEvent.GetMediaItemList)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistContent(
    state: WatchlistFavoritesState,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMediaClick: (mediaItem: MediaItem, mainPosterColor: Color) -> Unit,
    onOrderClick: () -> Unit,
    onRetryClick: () -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .setStatusNavigationBarColor(PrimaryColor)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarTitle(
                title = stringResource(state.screenType.titleResId),
                backgroundColor = PrimaryColor,
                onBackClick = onBackClick,
                onSearchClick = onSearchClick,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            return@Scaffold
        }

        if (state.isError) {
            Box(modifier = Modifier.fillMaxSize()) {
                ErrorAndRetry(
                    modifier = Modifier.align(Alignment.Center),
                    textColor = MaterialTheme.colorScheme.background.getOnBackgroundColor(),
                    errorMessage = stringResource(id = com.buntupana.tmdb.core.ui.R.string.message_loading_content_error),
                    onRetryClick = onRetryClick
                )
            }

            return@Scaffold
        }


        val pagerState = rememberPagerState(
            initialPage = state.defaultPage
        ) { MediaType.entries.size }

        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryColor),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                ScrollableTabRow(
                    modifier = Modifier.weight(1f),
                    contentColor = PrimaryColor.getOnBackgroundColor(),
                    edgePadding = 0.dp,
                    containerColor = PrimaryColor,
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = SecondaryColor,
                        )
                    }
                ) {
                    MediaFilter.entries.forEachIndexed { index, mediaType ->

                        Tab(
                            text = {
                                Row {
                                    val itemsCount = when (mediaType) {
                                        MediaFilter.MOVIES -> state.movieItemsTotalCount
                                        MediaFilter.TV_SHOWS -> state.tvShowItemsTotalCount
                                    }
                                    Text(text = stringResource(mediaType.strRes))

                                    Text(
                                        modifier = Modifier.padding(start = Dimens.padding.small),
                                        text = itemsCount?.toString() ?: ""
                                    )
                                }
                            },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                        )
                    }
                }

                OrderButtonAnimation(
                    modifier = Modifier,
                    textColor = PrimaryColor.getOnBackgroundColor(),
                    text = stringResource(RCore.string.text_last_added),
                    order = state.order,
                    onClick = onOrderClick
                )
            }

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                state = pagerState,
                beyondViewportPageCount = 1,
            ) { currentPage ->
                val (mediaNameResId, pagingItems) = when (currentPage) {
                    MediaFilter.entries.indexOf(MediaFilter.MOVIES) -> {
                        MediaFilter.MOVIES.strRes to state.movieItems?.collectAsLazyPagingItems()
                    }

                    MediaFilter.entries.indexOf(MediaFilter.TV_SHOWS) -> {
                        MediaFilter.TV_SHOWS.strRes to state.tvShowItems?.collectAsLazyPagingItems()
                    }

                    else -> MediaFilter.MOVIES.strRes to state.movieItems?.collectAsLazyPagingItems()
                }

                WatchlistPager(
                    modifier = Modifier.fillMaxSize(),
                    pagingItems = pagingItems,
                    noResultMessage = stringResource(
                        R.string.message_no_results,
                        stringResource(mediaNameResId),
                        stringResource(state.screenType.titleResId).lowercase()
                    ),
                    onMediaClick = onMediaClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun WatchlistScreenPreview() {
    WatchlistContent(
        WatchlistFavoritesState(
            isLoading = true,
            screenType = ScreenType.FAVORITES,
            isError = false,
            movieItemsTotalCount = 15,
            tvShowItemsTotalCount = null
        ),
        onBackClick = {},
        onSearchClick = {},
        onMediaClick = { _, _ -> },
        onOrderClick = {},
        onRetryClick = {}
    )
}
