package com.buntupana.tmdb.feature.search.presentation.comp

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.item.MediaItemHorizontal
import com.buntupana.tmdb.core.ui.composables.list.LazyColumnGeneric
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
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
    bottomPadding: Dp,
    onMediaClick: (mediaItem: MediaItem, mainPosterColor: Color) -> Unit,
    onPersonClick: (personId: Long) -> Unit
) {

    searchState.resultCountList ?: return

    val scope = rememberCoroutineScope()

    Column(modifier = modifier) {

        val pagerState = rememberPagerState(
            initialPage = searchState.defaultPage
        ) { searchState.resultCountList.size }

        // Adding a tab bar with result titles
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            Modifier.background(MaterialTheme.colorScheme.background),
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = MaterialTheme.colorScheme.secondaryContainer,
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
                        scope.launch {
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
            // Getting Result information for each page of the pager
            when (searchState.resultCountList[currentPage].searchType) {
                SearchType.MOVIE -> {
                    LazyColumnGeneric(
                        modifier = Modifier.fillMaxSize(),
                        topPadding = Dimens.padding.medium,
                        bottomPadding = { bottomPadding + Dimens.padding.small },
                        itemList = searchState.movieItems?.collectAsLazyPagingItems(),
                        noResultContent = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(Dimens.padding.medium),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = stringResource(R.string.message_movies_no_result))
                            }
                        },
                        itemContent = { index, item ->
                            MediaItemHorizontal(
                                modifier = Modifier.height(Dimens.imageSize.posterHeight),
                                onMediaClick = { _, mainPosterColor ->
                                    onMediaClick(item, mainPosterColor)
                                },
                                mediaId = item.id,
                                title = item.name,
                                posterUrl = item.posterUrl,
                                overview = item.overview,
                                releaseDate = item.releaseDate
                            )
                        }
                    )
                }

                SearchType.TV_SHOW -> {
                    LazyColumnGeneric(
                        modifier = Modifier.fillMaxSize(),
                        bottomPadding = { bottomPadding },
                        itemList = searchState.tvShowItems?.collectAsLazyPagingItems(),
                        noResultContent = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(Dimens.padding.medium),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = stringResource(R.string.message_tv_shows_no_result))
                            }
                        },
                        itemContent = { index, item ->
                            MediaItemHorizontal(
                                modifier = Modifier.height(Dimens.imageSize.posterHeight),
                                onMediaClick = { _, mainPosterColor ->
                                    onMediaClick(item, mainPosterColor)
                                },
                                mediaId = item.id,
                                title = item.name,
                                posterUrl = item.posterUrl,
                                overview = item.overview,
                                releaseDate = item.releaseDate
                            )
                        }
                    )
                }

                SearchType.PERSON -> {
                    LazyColumnGeneric(
                        modifier = Modifier.fillMaxSize(),
                        bottomPadding = { bottomPadding },
                        itemList = searchState.personItems?.collectAsLazyPagingItems(),
                        noResultContent = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(Dimens.padding.medium),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = stringResource(R.string.message_people_no_result))
                            }
                        },
                        itemContent = { index, item ->
                            val description = if (item.knownForList.firstOrNull() == null) {
                                item.knownForDepartment
                            } else {
                                "${item.knownForDepartment} • ${item.knownForList.first()}"
                            }

                            PersonItemHorizontal(
                                modifier = Modifier.height(Dimens.imageSize.personHeightSmall),
                                personId = item.id,
                                name = item.name,
                                profileUrl = item.profilePath,
                                gender = item.gender,
                                description = description,
                                onClick = onPersonClick
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true,
)
@Composable
fun SearchResultsPreview() {
    AppTheme {
        SearchResults(
            searchState = SearchState(
                resultCountList = listOf(
                    MediaResultCount(SearchType.MOVIE, 100),
                    MediaResultCount(SearchType.TV_SHOW, 87),
                    MediaResultCount(SearchType.PERSON, 10)
                )
            ),
            bottomPadding = 0.dp,
            onMediaClick = { _, _ -> },
            onPersonClick = {},
        )
    }
}