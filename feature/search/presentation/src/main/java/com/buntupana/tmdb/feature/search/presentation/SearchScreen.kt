package com.buntupana.tmdb.feature.search.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.util.SetSystemBarsColors
import com.buntupana.tmdb.feature.search.presentation.comp.SearchResults
import com.buntupana.tmdb.feature.search.presentation.comp.SearchSuggestionList
import com.buntupana.tmdb.feature.search.presentation.comp.SearchTopBar
import com.buntupana.tmdb.feature.search.presentation.comp.TrendingList
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.util.isNotNullOrEmpty
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onMediaClick: (mediaItemId: Long, mediaType: MediaType, mainPosterColor: Color?) -> Unit,
    onPersonClick: (personId: Long) -> Unit,
) {

    val focusManager = LocalFocusManager.current

    SearchScreenContent(
        state = viewModel.state,
        onSearchSuggestions = { viewModel.onEvent(SearchEvent.OnSearchSuggestions(it)) },
        onSearch = { searchKey, searchType ->
            viewModel.onEvent(SearchEvent.OnSearch(searchKey, searchType ?: SearchType.MOVIE))
        },
        onMediaClick = { mediaItem, mainPosterColor ->
            when (mediaItem) {
                is com.panabuntu.tmdb.core.common.model.MediaItem.Movie -> {
                    onMediaClick(
                        mediaItem.id,
                        MediaType.MOVIE,
                        mainPosterColor
                    )
                }

                is com.panabuntu.tmdb.core.common.model.MediaItem.TvShow -> {
                    onMediaClick(
                        mediaItem.id,
                        MediaType.TV_SHOW,
                        mainPosterColor
                    )
                }
            }
        },
        onPersonClick = onPersonClick,
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
    onMediaClick: (mediaItem: com.panabuntu.tmdb.core.common.model.MediaItem, mainPosterColor: Color?) -> Unit,
    onPersonClick: (personId: Long) -> Unit,
    onDismissSuggestionsClick: () -> Unit
) {

    SetSystemBarsColors(
        statusBarColor = MaterialTheme.colorScheme.onPrimaryContainer,
        navigationBarColor = MaterialTheme.colorScheme.background,
        translucentNavigationBar = true
    )

    Scaffold(
        topBar = {
            SearchTopBar(
                modifier = Modifier.fillMaxWidth(),
                searchKey = state.searchKey,
                onValueChanged = {
                    onSearchSuggestions(it)
                },
                isLoadingSuggestions = state.isSearchSuggestionsLoading,
                onSearch = { searchKey ->
                    onSearch(searchKey, null)
                },
                isLoadingSearch = state.isSearchLoading,
                requestFocus = true
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            when {
                state.isSearchLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.secondaryContainer)
                    }

                }

                state.isSearchError -> {
                    ErrorAndRetry(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                        errorMessage = stringResource(R.string.common_loading_content_error)
                    ) {
                        onSearch(state.searchKey, null)
                    }
                }

                state.resultCountList.isNotNullOrEmpty() -> {
                    SearchResults(
                        modifier = Modifier.fillMaxSize(),
                        bottomPadding = paddingValues.calculateBottomPadding(),
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

            SearchSuggestionList(
                suggestionList = state.searchSuggestionList,
                onSuggestionItemClick = { mediaItemName, searchType ->
                    onSearch(mediaItemName, searchType)
                },
                isSearchSuggestionError = state.isSearchSuggestionsError,
                onDismissSuggestionsClick = onDismissSuggestionsClick
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Composable
fun SearchScreenPreview() {

    val suggestionList = listOf(
        searchItemMovieSample,
        searchItemTVShowSample,
        searchItemPersonSample
    )

    AppTheme {
        SearchScreenContent(
            state = SearchState(
                searchKey = "asdf",
                resultCountList = listOf(MediaResultCount(SearchType.MOVIE, 100)),
                searchSuggestionList = suggestionList,
                isSearchError = false
            ),
            onSearchSuggestions = {},
            onSearch = { _, _ -> },
            onMediaClick = { _, _ -> },
            onPersonClick = {},
            onDismissSuggestionsClick = {}
        )
    }
}