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
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.R
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.composables.TextFieldSearch
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.theme.PrimaryColor
import com.buntupana.tmdb.core.presentation.theme.SecondaryColor
import com.buntupana.tmdb.core.presentation.theme.TertiaryColor

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