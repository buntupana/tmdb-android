package com.buntupana.tmdb.feature.search.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.composables.OutlinedText
import com.buntupana.tmdb.core.presentation.composables.TextFieldSearch
import com.buntupana.tmdb.core.presentation.composables.item.MediaItemHorizontal
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.theme.Primary
import com.buntupana.tmdb.core.presentation.theme.Secondary
import com.buntupana.tmdb.core.presentation.theme.Tertiary
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch

@Destination
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    searchNavigator: SearchNavigator
) {

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(color = Primary)
    }

    SearchScreenContent(
        searchState = viewModel.state,
        onSearchSuggestions = { viewModel.onEvent(SearchEvent.OnSearchSuggestions(it)) },
        onSearch = {
            viewModel.onEvent(SearchEvent.OnSearch(it))
        },
        onMediaClick = { mediaItem ->
            val mediaType = when (mediaItem) {
                is MediaItem.Movie -> MediaType.MOVIE
                is MediaItem.TvShow -> MediaType.TV_SHOW
                is MediaItem.Person -> null
                MediaItem.Unknown -> null
            }
            mediaType?.let {
                searchNavigator.navigateToMediaDetail(mediaItem.id, mediaType)
            }
        }
    )
}

@Composable
fun SearchScreenContent(
    searchState: SearchState,
    onSearchSuggestions: (searchKey: String) -> Unit,
    onSearch: (searchKey: String) -> Unit,
    onMediaClick: (mediaItem: MediaItem) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.topBarHeight),
            searchKey = searchState.searchKey,
            onValueChanged = {
                onSearchSuggestions(it)
            },
            isLoadingSuggestions = searchState.isSearchSuggestionsLoading,
            onSearch = {
                onSearch(it)
            },
            isLoadingSearch = searchState.isSearchLoading
        )

        Divider()

        when {
            searchState.isSearchLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Primary
                    )
                }
            }
            searchState.resultCountList.isNotEmpty() -> {
                SearchResults(
                    modifier = Modifier.fillMaxWidth(),
                    searchState = searchState,
                    onMediaClick = onMediaClick
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onValueChanged: (searchKey: String) -> Unit,
    searchKey: String,
    onSearch: (searchKey: String) -> Unit,
    isLoadingSuggestions: Boolean = false,
    isLoadingSearch: Boolean = false
) {
    Row(
        modifier = modifier
            .background(Primary)
            .padding(horizontal = Dimens.padding.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = com.buntupana.tmdb.core.R.drawable.ic_search),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Secondary)
        )
        TextFieldSearch(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = searchKey,
            onValueChange = {
                onValueChanged(it)
            },
            onSearch = {
                onSearch(it)
            },
            isEnabled = isLoadingSearch.not()
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier.size(24.dp)
        ) {
            if (!isLoadingSuggestions && searchKey.isNotBlank()) {
                val interactionSource = remember { MutableInteractionSource() }
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .indication(
                            indication = rememberRipple(color = Tertiary),
                            interactionSource = interactionSource
                        )
                        .clickable {
                            onValueChanged("")
                        },
                    painter = painterResource(id = com.buntupana.tmdb.core.R.drawable.ic_cancel),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Secondary)
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SearchResults(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    onMediaClick: (mediaItem: MediaItem) -> Unit
) {

    Column(modifier = modifier) {

        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState()

        ScrollableTabRow(
            // Our selected tab is our current page
            selectedTabIndex = pagerState.currentPage,
            // Override the indicator, using the provided pagerTabIndicatorOffset modifier
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = Secondary
                )
            },
            backgroundColor = MaterialTheme.colors.background,
        ) {
            // Add tabs for all of our pages
            searchState.resultCountList.forEachIndexed { index, resultCount ->
                Tab(
                    text = {
                        TabTitle(
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

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            count = searchState.resultCountList.size,
            state = pagerState
        ) { currentPage ->

            val itemHeight: Dp
            val noResultMessageStringRes: Int
            val items = when (searchState.resultCountList[currentPage].titleResId) {
                com.buntupana.tmdb.core.R.string.text_movies -> {
                    itemHeight = 120.dp
                    noResultMessageStringRes =
                        com.buntupana.tmdb.core.R.string.message_movies_no_result
                    searchState.movieItems.collectAsLazyPagingItems()
                }
                com.buntupana.tmdb.core.R.string.text_tv_shows -> {
                    itemHeight = 120.dp
                    noResultMessageStringRes =
                        com.buntupana.tmdb.core.R.string.message_tv_shows_no_result
                    searchState.tvShowItems.collectAsLazyPagingItems()
                }
                com.buntupana.tmdb.core.R.string.text_people -> {
                    itemHeight = 60.dp
                    noResultMessageStringRes =
                        com.buntupana.tmdb.core.R.string.message_people_no_result
                    searchState.personItems.collectAsLazyPagingItems()
                }
                else -> {
                    noResultMessageStringRes = 0
                    itemHeight = 0.dp
                    null
                }
            }

            if (items != null)
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    when (items.loadState.refresh) {
                        LoadState.Loading -> {
                            item {
                                Column(
                                    Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Spacer(modifier = Modifier.height(Dimens.padding.medium))
                                    CircularProgressIndicator(
                                        color = Primary
                                    )
                                }
                            }
                        }
                        is LoadState.Error -> {}
                        is LoadState.NotLoading -> {
                            if (items.itemCount == 0) {
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
                                items(items) { item ->
                                    item?.let {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        MediaItemHorizontal(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(itemHeight)
                                                .padding(horizontal = Dimens.padding.medium),
                                            mediaItem = item,
                                            clickable = {
                                                onMediaClick(item)
                                            }
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                                if (items.loadState.append is LoadState.Loading) {
                                    item {
                                        Column(
                                            Modifier.fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Spacer(modifier = Modifier.height(Dimens.padding.medium))
                                            CircularProgressIndicator(
                                                color = Primary
                                            )
                                        }
                                    }
                                }
                                item {
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                            }
                        }
                    }
                    // When we have no result we set a message
                }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun TabTitle(
    @StringRes titleResId: Int,
    resultCount: Int,
    isSelected: Boolean
) {

    val textColor: Color
    val outLineColor: Color
    val fontWeight: FontWeight

    if (isSelected) {
        textColor = Secondary
        outLineColor = Secondary
        fontWeight = FontWeight.Bold
    } else {
        textColor = Color.Black
        outLineColor = Color.Gray
        fontWeight = FontWeight.Normal
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = titleResId),
            fontWeight = fontWeight,
            color = textColor
        )
        Spacer(modifier = Modifier.width(4.dp))
        OutlinedText(
            text = resultCount.toString(),
            cornerRound = 6.dp,
            horizontalPadding = 8.dp,
            outlineColor = outLineColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreenContent(
        searchState = SearchState(),
        onSearchSuggestions = {},
        onSearch = {},
        onMediaClick = {}
    )
}