package com.buntupana.tmdb.feature.search.presentation.comp

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.TextFieldSearch
import com.buntupana.tmdb.core.ui.composables.widget.AppIconButton
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens

@Composable
fun SearchTopBar(
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
            .background(MaterialTheme.colorScheme.primaryContainer)
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(barHeight)
            .padding(horizontal = Dimens.padding.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondaryContainer)
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
            cursorColor = MaterialTheme.colorScheme.secondaryContainer
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier.size(24.dp)
        ) {
            if (!isLoadingSuggestions && searchKey.isNotBlank()) {
                AppIconButton(
                    onClick = {
                        onValueChanged("")
                    },
                    modifier = Modifier.clickable { onValueChanged("") },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cancel),
                        tint = MaterialTheme.colorScheme.secondaryContainer,
                        contentDescription = null
                    )
                }
            } else if (isLoadingSuggestions) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
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
fun SearchBarPreview() {
    AppTheme {
        SearchTopBar(
            onValueChanged = {},
            searchKey = "hola",
            onSearch = {},
        )
    }
}