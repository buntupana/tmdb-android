package com.buntupana.tmdb.feature.search.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.composables.ErrorAndRetry
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.theme.PrimaryColor
import com.buntupana.tmdb.core.presentation.util.setStatusNavigationBarColor
import com.buntupana.tmdb.feature.search.presentation.comp.SearchBar
import com.buntupana.tmdb.feature.search.presentation.comp.SearchResults
import com.buntupana.tmdb.feature.search.presentation.comp.TrendingList

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    searchNavigator: SearchNavigator
) {

    val focusManager = LocalFocusManager.current

    SearchScreenContent(
        state = viewModel.state,
        onSearchSuggestions = { viewModel.onEvent(SearchEvent.OnSearchSuggestions(it)) },
        onSearch = { searchKey, searchType ->
            viewModel.onEvent(SearchEvent.OnSearch(searchKey, searchType))
        },
        onMediaClick = { mediaItem, mainPosterColor ->
            when (mediaItem) {
                is MediaItem.Movie -> {
                    searchNavigator.navigateToMediaDetail(
                        mediaItem.id,
                        MediaType.MOVIE,
                        mainPosterColor
                    )
                }

                is MediaItem.TvShow -> {
                    searchNavigator.navigateToMediaDetail(
                        mediaItem.id,
                        MediaType.TV_SHOW,
                        mainPosterColor
                    )
                }
            }
        },
        onPersonClick = { personId ->
            searchNavigator.navigateToPerson(personId)
        },
        onDismissSuggestionsClick = {
            focusManager.clearFocus()
            viewModel.onEvent(SearchEvent.DismissSuggestions)
        }
    )
}

@Composable
fun SearchScreenContent(
    state: SearchState,
    onSearchSuggestions: (searchKey: String) -> Unit,
    onSearch: (searchKey: String, searchType: SearchType?) -> Unit,
    onMediaClick: (mediaItem: MediaItem, mainPosterColor: Color?) -> Unit,
    onPersonClick: (personId: Long) -> Unit,
    onDismissSuggestionsClick: () -> Unit
) {

    Box(
        modifier = Modifier.setStatusNavigationBarColor()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = Dimens.topBarHeight)
        ) {
            when {
                state.isSearchLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = PrimaryColor)
                    }

                }

                state.isSearchError -> {
                    ErrorAndRetry(
                        modifier = Modifier
                            .padding(vertical = 200.dp)
                            .fillMaxSize(),
                        errorMessage = stringResource(com.buntupana.tmdb.core.R.string.message_loading_content_error)
                    ) {
                        onSearch(state.searchKey, null)
                    }
                }

                state.resultCountList.isNotEmpty() -> {
                    SearchResults(
                        modifier = Modifier.fillMaxWidth(),
                        searchState = state,
                        onMediaClick = onMediaClick,
                        onPersonClick = onPersonClick
                    )
                }

                else -> {
                    TrendingList(
                        modifier = Modifier.fillMaxWidth(),
                        mediaItemList = state.trendingList,
                        clickable = {
                            onSearch(it.name, null)
                        }
                    )
                }
            }
        }

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            searchKey = state.searchKey,
            onValueChanged = {
                onSearchSuggestions(it)
            },
            isLoadingSuggestions = state.isSearchSuggestionsLoading,
            onSearch = { searchKey ->
                onSearch(searchKey, null)
            },
            isLoadingSearch = state.isSearchLoading,
            requestFocus = true,
            suggestionList = state.searchSuggestionList,
            isSearchSuggestionError = state.isSearchSuggestionsError,
            onSuggestionItemClick = { mediaItemName, searchType ->
                onSearch(mediaItemName, searchType)
            },
            onDismissSuggestionsClick = onDismissSuggestionsClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreenContent(
        state = SearchState(
            searchSuggestionList = null,
            isSearchError = true
        ),
        onSearchSuggestions = {},
        onSearch = { _, _ -> },
        onMediaClick = { _, _ -> },
        onPersonClick = {},
        onDismissSuggestionsClick = {}
    )
}