package com.buntupana.tmdb.feature.search.presentation.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.feature.search.presentation.MediaResultCount
import com.buntupana.tmdb.feature.search.presentation.SearchState
import com.buntupana.tmdb.feature.search.presentation.SearchType
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.launch
import timber.log.Timber

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

        var defaultPageSelector by remember { mutableStateOf(true) }

        LaunchedEffect(defaultPageSelector) {
            defaultPageSelector = false
            val defaultIndex =
                searchState.resultCountList.indexOfFirst { it.searchType == searchState.defaultSearchType }
            pagerState.requestScrollToPage(defaultIndex)
        }

        // Adding a tab bar with result titles
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            Modifier.background(MaterialTheme.colorScheme.background),
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = SecondaryColor,
                )
            }
        ) {
            // Add tabs for all of our pages
            searchState.resultCountList.forEachIndexed { index, resultCount ->

                val titleResId = when (resultCount.searchType) {
                    SearchType.MOVIE -> R.string.text_movies
                    SearchType.TV_SHOW -> R.string.text_tv_shows
                    SearchType.PERSON -> R.string.text_people
                }

                Tab(
                    text = {
                        TabSearchResult(
                            titleResId = titleResId,
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
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            state = pagerState,
            beyondViewportPageCount = 2
        ) { currentPage ->

            Timber.d("SearchResults() called with: currentPage = [$currentPage]")
            val noResultMessageStringRes: Int
            // Getting Result information for each page of the pager
            val pagingItems = when (searchState.resultCountList[currentPage].searchType) {
                SearchType.MOVIE -> {
                    noResultMessageStringRes = R.string.message_movies_no_result
                    searchState.movieItems.collectAsLazyPagingItems()
                }

                SearchType.TV_SHOW -> {
                    noResultMessageStringRes = R.string.message_tv_shows_no_result
                    searchState.tvShowItems.collectAsLazyPagingItems()
                }

                SearchType.PERSON -> {
                    noResultMessageStringRes = R.string.message_people_no_result
                    searchState.personItems.collectAsLazyPagingItems()
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

@Preview
@Composable
fun SearchResultsPreview() {
    SearchResults(
        searchState = SearchState(
            resultCountList = listOf(
                MediaResultCount(SearchType.MOVIE, 100),
                MediaResultCount(SearchType.TV_SHOW, 87),
                MediaResultCount(SearchType.PERSON, 10)
            )
        ),
        onMediaClick = { _, _ -> },
        onPersonClick = {}
    )
}