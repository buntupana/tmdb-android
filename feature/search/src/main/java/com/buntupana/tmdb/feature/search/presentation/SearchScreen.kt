package com.buntupana.tmdb.feature.search.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.buntupana.tmdb.feature.search.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import com.buntupana.tmdb.core.R as RCore

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
        },
        onDismissSuggestionsClick = {
            viewModel.onEvent(SearchEvent.DismissSuggestions)
        }
    )
}

@Composable
fun SearchScreenContent(
    searchState: SearchState,
    onSearchSuggestions: (searchKey: String) -> Unit,
    onSearch: (searchKey: String) -> Unit,
    onMediaClick: (mediaItem: MediaItem) -> Unit,
    onDismissSuggestionsClick: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = Dimens.topBarHeight)
        ) {
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
                searchState.resultCountList.isEmpty() -> {
                    TrendingList(
                        modifier = Modifier.fillMaxWidth(),
                        mediaItemList = searchState.trendingList,
                        clickable = {
                            onSearch(it.name)
                        }
                    )
                }
            }
        }

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            searchKey = searchState.searchKey,
            onValueChanged = {
                onSearchSuggestions(it)
            },
            isLoadingSuggestions = searchState.isSearchSuggestionsLoading,
            onSearch = onSearch,
            isLoadingSearch = searchState.isSearchLoading,
            requestFocus = true,
            suggestionList = searchState.searchSuggestionList,
            clickable = {
                onSearch(it.name)
            },
            onDismissSuggestionsClick = onDismissSuggestionsClick
        )
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    barHeight: Dp = Dimens.topBarHeight,
    onValueChanged: (searchKey: String) -> Unit,
    searchKey: String,
    onSearch: (searchKey: String) -> Unit,
    isLoadingSuggestions: Boolean = false,
    isLoadingSearch: Boolean = false,
    requestFocus: Boolean = false,
    suggestionList: List<MediaItem>,
    clickable: (mediaItem: MediaItem) -> Unit,
    onDismissSuggestionsClick: () -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .height(barHeight)
                .background(Primary)
                .padding(horizontal = Dimens.padding.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = RCore.drawable.ic_search),
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
                isEnabled = isLoadingSearch.not(),
                requestFocus = requestFocus,
                cursorColor = Secondary
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
                        painter = painterResource(id = RCore.drawable.ic_cancel),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Secondary)
                    )
                } else if (isLoadingSuggestions) {
                    CircularProgressIndicator(
                        color = Secondary
                    )
                }
            }
        }

        if (suggestionList.isNotEmpty()) {
            val listState = rememberLazyListState()
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = listState
            ) {
                items(suggestionList) {
                    SuggestionItem(
                        mediaItem = it,
                        clickable = clickable
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .clickable {
                        onDismissSuggestionsClick()
                    }
            )
        }
    }
}

@Composable
fun TrendingList(
    modifier: Modifier = Modifier,
    mediaItemList: List<MediaItem>,
    clickable: (mediaItem: MediaItem) -> Unit
) {

    if (mediaItemList.isEmpty()) {
        return
    }

    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = listState
    ) {
        item {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Gray.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = Dimens.padding.medium,
                            vertical = Dimens.padding.small
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_trending_icon),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(Dimens.padding.small))
                    Text(
                        text = stringResource(id = RCore.string.text_trending),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider(
                    color = Primary
                )
            }
        }

        items(mediaItemList) { mediaItem ->
            SuggestionItem(
                mediaItem = mediaItem,
                clickable = clickable
            )
        }
    }
}

@Composable
fun SuggestionItem(
    mediaItem: MediaItem,
    itemHeight: Dp = 40.dp,
    clickable: (mediaItem: MediaItem) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight)
            .background(MaterialTheme.colors.background)
            .clickable {
                focusManager.clearFocus()
                clickable(mediaItem)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.padding.medium,
                    vertical = Dimens.padding.small
                )
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = RCore.drawable.ic_search),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
            )
            Spacer(modifier = Modifier.width(Dimens.padding.small))
            Text(text = mediaItem.name)
        }
        Divider(
            color = Primary,
        )
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

        // Adding a tab bar with result titles
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
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
            count = searchState.resultCountList.size,
            state = pagerState
        ) { currentPage ->

            val itemHeight: Dp
            val noResultMessageStringRes: Int
            // Getting Result information for each page of the pager
            val items = when (searchState.resultCountList[currentPage].titleResId) {
                RCore.string.text_movies -> {
                    itemHeight = 120.dp
                    noResultMessageStringRes =
                        RCore.string.message_movies_no_result
                    searchState.movieItems.collectAsLazyPagingItems()
                }
                RCore.string.text_tv_shows -> {
                    itemHeight = 120.dp
                    noResultMessageStringRes =
                        RCore.string.message_tv_shows_no_result
                    searchState.tvShowItems.collectAsLazyPagingItems()
                }
                RCore.string.text_people -> {
                    itemHeight = 60.dp
                    noResultMessageStringRes =
                        RCore.string.message_people_no_result
                    searchState.personItems.collectAsLazyPagingItems()
                }
                else -> {
                    noResultMessageStringRes = 0
                    itemHeight = 0.dp
                    null
                }
            }

            if (items != null) {
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
                        is LoadState.Error -> {
                            // TODO: Error to show when first page of paging fails
                        }
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

                                // Drawing result items
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

                                // Appending result strategy
                                when (items.loadState.append) {
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

@Composable
fun TabSearchResult(
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
        searchState = SearchState(
            searchSuggestionList = emptyList()
        ),
        onSearchSuggestions = {},
        onSearch = {},
        onMediaClick = {},
        onDismissSuggestionsClick = {}
    )
}