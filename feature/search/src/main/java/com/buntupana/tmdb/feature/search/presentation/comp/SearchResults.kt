package com.buntupana.tmdb.feature.search.presentation.comp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.buntupana.tmdb.core.R
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.composables.item.MediaItemHorizontal
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.theme.PrimaryColor
import com.buntupana.tmdb.core.presentation.theme.SecondaryColor
import com.buntupana.tmdb.feature.search.presentation.SearchState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResults(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    onMediaClick: (mediaItem: MediaItem, mainPosterColor: Color?) -> Unit
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

            val itemHeight: Dp
            val noResultMessageStringRes: Int
            // Getting Result information for each page of the pager
            val pagingItems = when (searchState.resultCountList[currentPage].titleResId) {
                R.string.text_movies -> {
                    itemHeight = 120.dp
                    noResultMessageStringRes =
                        R.string.message_movies_no_result
                    searchState.movieItems.collectAsLazyPagingItems()
                }

                R.string.text_tv_shows -> {
                    itemHeight = 120.dp
                    noResultMessageStringRes =
                        R.string.message_tv_shows_no_result
                    searchState.tvShowItems.collectAsLazyPagingItems()
                }

                R.string.text_people -> {
                    itemHeight = 60.dp
                    noResultMessageStringRes =
                        R.string.message_people_no_result
                    searchState.personItems.collectAsLazyPagingItems()
                }

                else -> {
                    noResultMessageStringRes = 0
                    itemHeight = 0.dp
                    null
                }
            }

            if (pagingItems != null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    when (pagingItems.loadState.refresh) {
                        LoadState.Loading -> {
                            item {
                                Column(
                                    Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Spacer(modifier = Modifier.height(Dimens.padding.medium))
                                    CircularProgressIndicator(
                                        color = PrimaryColor
                                    )
                                }
                            }
                        }

                        is LoadState.Error -> {
                            // TODO: Error to show when first page of paging fails
                        }

                        is LoadState.NotLoading -> {
                            if (pagingItems.itemCount == 0) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(Dimens.padding.medium)
                                    ) {
                                        Text(text = stringResource(id = noResultMessageStringRes))
                                    }
                                }
                            } else {
                                // Setting result items
                                item {
                                    Spacer(modifier = Modifier.height(4.dp))
                                }

                                // Drawing result items
                                items(pagingItems.itemCount) { index ->
                                    val item = pagingItems[index]
                                    item?.let {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        MediaItemHorizontal(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(itemHeight)
                                                .padding(horizontal = Dimens.padding.medium),
                                            mediaItem = item,
                                            clickable = { color ->
                                                onMediaClick(item, color)
                                            }
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }

                                // Appending result strategy
                                when (pagingItems.loadState.append) {
                                    LoadState.Loading -> {
                                        item {
                                            Column(
                                                Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Spacer(modifier = Modifier.height(Dimens.padding.medium))
                                                CircularProgressIndicator(
                                                    color = PrimaryColor
                                                )
                                            }
                                        }
                                    }

                                    is LoadState.Error -> {
                                        // TODO: item to show when append paging fails
                                    }

                                    else -> {}
                                }
                                item {
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}