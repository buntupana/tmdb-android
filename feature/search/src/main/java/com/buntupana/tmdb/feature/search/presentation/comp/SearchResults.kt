package com.buntupana.tmdb.feature.search.presentation.comp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.buntupana.tmdb.core.R
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.theme.SecondaryColor
import com.buntupana.tmdb.feature.search.presentation.SearchState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResults(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    onMediaClick: (mediaItem: MediaItem, mainPosterColor: Color) -> Unit,
    onPersonClick: (personId: Long) -> Unit
) {

    Column(modifier = modifier) {

        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState { searchState.resultCountList.size }

        // Adding a tab bar with result titles
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = SecondaryColor
                )
            },
            backgroundColor = MaterialTheme.colors.background,
        ) {
            // Add tabs for all of our pages
            searchState.resultCountList.forEachIndexed { index, resultCount ->
                Tab(
                    text = {
                        TabSearchResult(
                            titleResId = resultCount.titleResId,
                            resultCount = resultCount.resultCount,
                            isSelected = pagerState.currentPage == index
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }

        // Pager with all results pages
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ) { currentPage ->

            val noResultMessageStringRes: Int
            // Getting Result information for each page of the pager
            val pagingItems = when (searchState.resultCountList[currentPage].titleResId) {
                R.string.text_movies -> {
                    noResultMessageStringRes =
                        R.string.message_movies_no_result
                    searchState.movieItems.collectAsLazyPagingItems()
                }

                R.string.text_tv_shows -> {
                    noResultMessageStringRes =
                        R.string.message_tv_shows_no_result
                    searchState.tvShowItems.collectAsLazyPagingItems()
                }

                R.string.text_people -> {
                    noResultMessageStringRes =
                        R.string.message_people_no_result
                    searchState.personItems.collectAsLazyPagingItems()
                }

                else -> {
                    noResultMessageStringRes = 0
                    null
                }
            }

            SearchPager(
                modifier = Modifier.fillMaxSize(),
                pagingItems = pagingItems,
                noResultMessage = stringResource(id = noResultMessageStringRes),
                onMediaClick = onMediaClick,
                onPersonClick = onPersonClick
            )

            Spacer(modifier = Modifier.height(Dimens.padding.tiny))
        }
    }
}