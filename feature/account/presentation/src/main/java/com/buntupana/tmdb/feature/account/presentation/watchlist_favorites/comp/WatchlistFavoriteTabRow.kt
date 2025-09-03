package com.buntupana.tmdb.feature.account.presentation.watchlist_favorites.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import com.buntupana.tmdb.core.ui.composables.VerticalTextRoulette
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
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
            .background(MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        ScrollableTabRow(
            modifier = Modifier.weight(1f),
            contentColor = MaterialTheme.colorScheme.onPrimary,
            edgePadding = 0.dp,
            containerColor = MaterialTheme.colorScheme.primary,
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        ) {
            MediaFilter.entries.forEachIndexed { index, mediaType ->

                Tab(
                    text = {
                        Row {
                            val itemsCount = when (mediaType) {
                                MediaFilter.MOVIES -> movieItemsTotalCount
                                MediaFilter.TV_SHOWS -> tvShowItemsTotalCount
                            }

                            Text(text = stringResource(mediaType.strRes))

                            VerticalTextRoulette(
                                text = " $itemsCount"
                            )
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
            textColor = MaterialTheme.colorScheme.onPrimary,
            text = stringResource(R.string.text_last_added),
            order = order,
            onClick = onOrderClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WatchlistFavoriteTabRowPreview() {
    WatchlistFavoriteTabRow(
        modifier = Modifier,
        pagerState = rememberPagerState { 2 },
        order = Order.DESC,
        movieItemsTotalCount = 15,
        tvShowItemsTotalCount = null,
        onOrderClick = {}
    )
}