package com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.comp

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.OrderButtonAnimation
import com.buntupana.tmdb.core.ui.composables.VerticalNumberRoulette
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.panabuntu.tmdb.core.common.model.Order
import kotlinx.coroutines.launch

@Composable
fun WatchlistFavoriteTabRow(
    modifier: Modifier,
    pagerState: PagerState,
    order: Order,
    movieItemsTotalCount: Int?,
    tvShowItemsTotalCount: Int?,
    onOrderClick: () -> Unit
) {

    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        SecondaryScrollableTabRow(
            modifier = Modifier.weight(1f),
            contentColor = MaterialTheme.colorScheme.primaryContainer.getOnBackgroundColor(),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            edgePadding = 0.dp,
            selectedTabIndex = pagerState.currentPage,
            divider = {},
            indicator = {
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(pagerState.currentPage),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                )
            }
        ) {
            MediaFilter.entries.forEachIndexed { index, mediaType ->

                Tab(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val itemsCount = when (mediaType) {
                                MediaFilter.MOVIES -> movieItemsTotalCount
                                MediaFilter.TV_SHOWS -> tvShowItemsTotalCount
                            }

                            Text(text = stringResource(mediaType.strRes) + "  ")

                            VerticalNumberRoulette(value = itemsCount)
                        }
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }

        OrderButtonAnimation(
            modifier = Modifier,
            textColor = MaterialTheme.colorScheme.primaryContainer.getOnBackgroundColor(),
            text = stringResource(R.string.text_last_added),
            order = order,
            onClick = onOrderClick
        )
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
private fun WatchlistFavoriteTabRowPreview() {
    AppTheme {
        WatchlistFavoriteTabRow(
            modifier = Modifier,
            pagerState = rememberPagerState { 2 },
            order = Order.DESC,
            movieItemsTotalCount = 15,
            tvShowItemsTotalCount = 12,
            onOrderClick = {}
        )
    }
}