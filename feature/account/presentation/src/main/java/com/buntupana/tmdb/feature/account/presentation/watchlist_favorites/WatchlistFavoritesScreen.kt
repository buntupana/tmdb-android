package com.buntupana.tmdb.feature.account.presentation.watchlist_favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.buntupana.tmdb.core.ui.composables.CircularProgressIndicatorDelayed
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.top_bar.TopBarTitle
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.util.SetLegacySystemBarsColors
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.util.isLight
import com.buntupana.tmdb.core.ui.util.isVisible
import com.buntupana.tmdb.core.ui.util.mediaItemMovie
import com.buntupana.tmdb.core.ui.util.paddingValues
import com.buntupana.tmdb.core.ui.util.setStatusBarLightStatusFromBackground
import com.buntupana.tmdb.feature.account.presentation.R
import com.buntupana.tmdb.feature.account.presentation.watchlist_favorites.comp.WatchlistFavoritePager
import com.buntupana.tmdb.feature.account.presentation.watchlist_favorites.comp.WatchlistFavoriteTabRow
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.flow.flow

@Composable
fun WatchlistScreen(
    viewModel: WatchlistFavoritesViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMediaClick: (mediaItemId: Long, mediaType: MediaType, mainPosterColor: Color?) -> Unit,
) {
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

    setStatusBarLightStatusFromBackground(
        LocalView.current,
        PrimaryColor
    )

    SetLegacySystemBarsColors(
        statusBarColor = PrimaryColor,
        navigationBarColor = PrimaryColor,
        useDarkStatusBarIcons = PrimaryColor.isLight(),
        useDarkNavigationBarIcons = PrimaryColor.isLight()
    )

    Scaffold(
        modifier = Modifier
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

        val movieItems = state.movieItems?.collectAsLazyPagingItems()
        val tvShowItems = state.tvShowItems?.collectAsLazyPagingItems()

        if (
            state.isLoading ||
            movieItems?.loadState?.refresh is LoadState.Loading ||
            tvShowItems?.loadState?.refresh is LoadState.Loading
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicatorDelayed(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        if (state.isError) {
            ErrorAndRetry(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = paddingValues.calculateTopPadding() + Dimens.errorAndRetryTopPadding),
                textColor = MaterialTheme.colorScheme.background.getOnBackgroundColor(),
                errorMessage = stringResource(id = com.buntupana.tmdb.core.ui.R.string.message_loading_content_error),
                onRetryClick = onRetryClick
            )
        }

        val pagerState = rememberPagerState(
            initialPage = state.defaultPage
        ) { MediaType.entries.size }

        Column(
            modifier = Modifier.paddingValues(top = { paddingValues.calculateTopPadding() })
        ) {

            WatchlistFavoriteTabRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .isVisible(
                        isVisible = state.movieItemsTotalCount != null && state.tvShowItemsTotalCount != null,
                        animateSize = true
                    ),
                pagerState = pagerState,
                order = state.order,
                movieItemsTotalCount = state.movieItemsTotalCount,
                tvShowItemsTotalCount = state.tvShowItemsTotalCount,
                onOrderClick = onOrderClick
            )

            if (state.isError || state.isLoading) {
                return@Scaffold
            }

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize(),
                state = pagerState,
                beyondViewportPageCount = 1,
            ) { currentPage ->

                val (mediaNameResId, pagingItems) = when (currentPage) {
                    MediaFilter.entries.indexOf(MediaFilter.MOVIES) -> {
                        MediaFilter.MOVIES.strRes to movieItems
                    }

                    MediaFilter.entries.indexOf(MediaFilter.TV_SHOWS) -> {
                        MediaFilter.TV_SHOWS.strRes to tvShowItems
                    }

                    else -> MediaFilter.MOVIES.strRes to movieItems
                }

                WatchlistFavoritePager(
                    modifier = Modifier,
                    navigationBarPadding = paddingValues.calculateBottomPadding(),
                    pagingItems = pagingItems,
                    noResultMessage = stringResource(
                        R.string.message_no_results_in,
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
            isLoading = false,
            screenType = ScreenType.FAVORITES,
            isError = true,
            movieItemsTotalCount = 15,
            tvShowItemsTotalCount = 40,
            movieItems = flow {
                PagingData.from(
                    listOf(mediaItemMovie, mediaItemMovie),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.NotLoading(true),
                        prepend = LoadState.NotLoading(true),
                        append = LoadState.NotLoading(true)
                    )
                )
            }
        ),
        onBackClick = {},
        onSearchClick = {},
        onMediaClick = { _, _ -> },
        onOrderClick = {},
        onRetryClick = {}
    )
}
