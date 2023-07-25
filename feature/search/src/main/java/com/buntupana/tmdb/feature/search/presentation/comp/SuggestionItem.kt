package com.buntupana.tmdb.feature.search.presentation.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.R
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.theme.Primary

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
                painter = painterResource(id = R.drawable.ic_search),
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