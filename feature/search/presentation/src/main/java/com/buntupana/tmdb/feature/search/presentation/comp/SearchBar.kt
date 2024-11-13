package com.buntupana.tmdb.feature.search.presentation.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.TextFieldSearch
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.core.ui.theme.TertiaryColor
import com.buntupana.tmdb.feature.search.presentation.SearchType
import com.buntupana.tmdb.feature.search.presentation.searchItemMovieSample
import com.buntupana.tmdb.feature.search.presentation.searchItemPersonSample
import com.buntupana.tmdb.feature.search.presentation.searchItemTVShowSample

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
    suggestionList: List<com.buntupana.tmdb.feature.search.domain.model.SearchItem>?,
    onSuggestionItemClick: (
        mediaItemName: String,
        searchType: SearchType?
    ) -> Unit,
    isSearchSuggestionError: Boolean,
    onDismissSuggestionsClick: () -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .height(barHeight)
                .background(PrimaryColor)
                .padding(horizontal = Dimens.padding.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                colorFilter = ColorFilter.tint(SecondaryColor)
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
                cursorColor = SecondaryColor
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
                                indication = ripple(color = TertiaryColor),
                                interactionSource = interactionSource
                            )
                            .clickable {
                                onValueChanged("")
                            },
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(SecondaryColor)
                    )
                } else if (isLoadingSuggestions) {
                    CircularProgressIndicator(
                        color = SecondaryColor
                    )
                }
            }
        }

        if (suggestionList == null || searchKey.isBlank()) return@Column

        val errorTextResId = when {
            isSearchSuggestionError -> R.string.message_loading_content_error
            suggestionList.isEmpty() && searchKey.isNotBlank() -> R.string.text_no_results
            else -> null
        }

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
                                    is com.buntupana.tmdb.feature.search.domain.model.SearchItem.Movie -> SearchType.MOVIE
                                    is com.buntupana.tmdb.feature.search.domain.model.SearchItem.Person -> SearchType.PERSON
                                    is com.buntupana.tmdb.feature.search.domain.model.SearchItem.TvShow -> SearchType.TV_SHOW
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

@Preview
@Composable
fun SearchBarPreview() {

    val suggestionList = listOf(
        searchItemMovieSample,
        searchItemTVShowSample,
        searchItemPersonSample
    )

    SearchBar(
        onValueChanged = {},
        searchKey = "hola",
        onSearch = {},
        suggestionList = suggestionList,
        isSearchSuggestionError = true,
        onSuggestionItemClick = { _, _ -> },
        onDismissSuggestionsClick = {}
    )
}