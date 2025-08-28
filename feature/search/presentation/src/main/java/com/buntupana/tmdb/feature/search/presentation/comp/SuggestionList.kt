package com.buntupana.tmdb.feature.search.presentation.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.search.domain.model.SearchItem
import com.buntupana.tmdb.feature.search.presentation.SearchType
import com.buntupana.tmdb.feature.search.presentation.searchItemMovieSample
import com.buntupana.tmdb.feature.search.presentation.searchItemPersonSample
import com.buntupana.tmdb.feature.search.presentation.searchItemTVShowSample

@Composable
fun SuggestionList(
    modifier: Modifier = Modifier,
    suggestionList: List<SearchItem>?,
    onSuggestionItemClick: (mediaItemName: String, searchType: SearchType?) -> Unit,
    isSearchSuggestionError: Boolean,
    onDismissSuggestionsClick: () -> Unit
) {

    if (suggestionList == null) return

    val errorTextResId = when {
        isSearchSuggestionError -> R.string.message_loading_content_error
        suggestionList.isEmpty() -> R.string.text_no_results
        else -> null
    }
    Column(modifier = modifier) {
        if (errorTextResId != null) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(vertical = Dimens.padding.big),
                text = stringResource(errorTextResId),
                textAlign = TextAlign.Center
            )
        } else {
            val listState = rememberLazyListState()
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = listState
            ) {
                items(suggestionList) { searchItem ->
                    SuggestionItem(
                        mediaItem = searchItem,
                        showItemIcon = searchItem.isHighlighted,
                        clickable = {

                            val searchType: SearchType? = if (searchItem.isHighlighted) {
                                when (searchItem) {
                                    is SearchItem.Movie -> SearchType.MOVIE
                                    is SearchItem.Person -> SearchType.PERSON
                                    is SearchItem.TvShow -> SearchType.TV_SHOW
                                }
                            } else {
                                null
                            }
                            onSuggestionItemClick(searchItem.name, searchType)
                        }
                    )
                }
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

@Preview(showBackground = true)
@Composable
private fun SuggestionListPreview() {

    val suggestionList = listOf(
        searchItemMovieSample,
        searchItemTVShowSample,
        searchItemPersonSample
    )

    SuggestionList(
        modifier = Modifier.fillMaxSize(),
        onSuggestionItemClick = { _, _ -> },
        isSearchSuggestionError = true,
        onDismissSuggestionsClick = {},
        suggestionList = suggestionList
    )
}