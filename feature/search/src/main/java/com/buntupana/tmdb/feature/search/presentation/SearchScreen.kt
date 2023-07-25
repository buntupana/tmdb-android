package com.buntupana.tmdb.feature.search.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.theme.Primary
import com.buntupana.tmdb.feature.search.presentation.comp.SearchBar
import com.buntupana.tmdb.feature.search.presentation.comp.SearchResults
import com.buntupana.tmdb.feature.search.presentation.comp.TrendingList
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination

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
        onMediaClick = { mediaItem, mainPosterColor ->
            when (mediaItem) {
                is MediaItem.Movie -> {
                    searchNavigator.navigateToMediaDetail(mediaItem.id, MediaType.MOVIE, mainPosterColor)
                }

                is MediaItem.TvShow -> {
                    searchNavigator.navigateToMediaDetail(mediaItem.id, MediaType.TV_SHOW, mainPosterColor)
                }

                is MediaItem.Person -> {
                    searchNavigator.navigateToPerson(mediaItem.id)
                }

                MediaItem.Unknown -> {}
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
    onMediaClick: (mediaItem: MediaItem, mainPosterColor: Color?) -> Unit,
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

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreenContent(
        searchState = SearchState(
            searchSuggestionList = emptyList()
        ),
        onSearchSuggestions = {},
        onSearch = {},
        onMediaClick = { _, _ ->},
        onDismissSuggestionsClick = {}
    )
}