package com.buntupana.tmdb.feature.search.presentation.comp

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.search.domain.model.SearchItem
import com.buntupana.tmdb.feature.search.presentation.SearchType
import com.buntupana.tmdb.feature.search.presentation.searchItemMovieSample
import com.buntupana.tmdb.feature.search.presentation.searchItemPersonSample
import com.buntupana.tmdb.feature.search.presentation.searchItemTVShowSample
import com.panabuntu.tmdb.core.common.util.isNotNullOrEmpty

@Composable
fun SearchSuggestionList(
    modifier: Modifier = Modifier,
    suggestionList: List<SearchItem>?,
    onSuggestionItemClick: (mediaItemName: String, searchType: SearchType?) -> Unit,
    isSearchSuggestionError: Boolean,
    onDismissSuggestionsClick: () -> Unit
) {

    val errorTextResId = when {
        isSearchSuggestionError -> R.string.common_loading_content_error
        suggestionList != null && suggestionList.isEmpty() -> R.string.common_no_results
        else -> null
    }

    var showSearchSuggestions by remember { mutableStateOf(suggestionList.isNotNullOrEmpty() && errorTextResId != null) }

    showSearchSuggestions = suggestionList.isNotNullOrEmpty() && errorTextResId != null

    Column(modifier = modifier) {

        if (suggestionList == null) return@Column

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .animateContentSize()
        ) {
//            AnimatedVisibility(
//                visible = showSearchSuggestions,
//                enter = slideInVertically(),
//                exit = slideOutVertically()
//            ) {
                if (errorTextResId != null) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.padding.big),
                        text = stringResource(errorTextResId),
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        state = rememberLazyListState()
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
//            }
        }

//        AnimatedVisibility(
//            visible = showSearchSuggestions,
//            enter = fadeIn(),
//            exit = fadeOut()
//        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .animateContentSize()
                    .clickable {
                        onDismissSuggestionsClick()
                    }
            )
        }
//    }
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
private fun SearchSuggestionListPreview() {

    val suggestionList = listOf(
        searchItemMovieSample,
        searchItemTVShowSample,
        searchItemPersonSample
    )

    AppTheme {
        SearchSuggestionList(
            modifier = Modifier.fillMaxSize(),
            onSuggestionItemClick = { _, _ -> },
            isSearchSuggestionError = true,
            onDismissSuggestionsClick = {},
            suggestionList = suggestionList
        )
    }
}