package com.buntupana.tmdb.feature.search.presentation.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.TextFieldSearch
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.core.ui.theme.TertiaryColor

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
) {
    Row(
        modifier = modifier
            .background(PrimaryColor)
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(barHeight)
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
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(
        onValueChanged = {},
        searchKey = "hola",
        onSearch = {},
    )
}