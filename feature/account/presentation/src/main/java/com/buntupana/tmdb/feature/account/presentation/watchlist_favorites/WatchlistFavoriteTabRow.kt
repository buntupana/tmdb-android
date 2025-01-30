package com.buntupana.tmdb.feature.account.presentation.watchlist_favorites

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
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
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.panabuntu.tmdb.core.common.model.Order
import kotlinx.coroutines.launch

@Composable
fun WatchlistFavoriteTabRow(
    modifier: Modifier,
    pagerState: PagerState,
    order: Order,
    isLoading: Boolean,
    isError: Boolean,
    movieItemsTotalCount: Int?,
    tvShowItemsTotalCount: Int?,
    onOrderClick: () -> Unit
) {

    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .background(PrimaryColor)
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        if (isError.not() && isLoading.not()) {

            ScrollableTabRow(
                modifier = Modifier.weight(1f),
                contentColor = PrimaryColor.getOnBackgroundColor(),
                edgePadding = 0.dp,
                containerColor = PrimaryColor,
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = SecondaryColor,
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

                                Text(
                                    modifier = Modifier.padding(start = Dimens.padding.small),
                                    text = itemsCount?.toString() ?: ""
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
                textColor = PrimaryColor.getOnBackgroundColor(),
                text = stringResource(R.string.text_last_added),
                order = order,
                onClick = onOrderClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WatchlistFavoriteTabRowPreview() {
    WatchlistFavoriteTabRow(
        modifier = Modifier,
        pagerState = rememberPagerState { 2 },
        order = Order.DESC,
        isLoading = false,
        isError = false,
        movieItemsTotalCount = 15,
        tvShowItemsTotalCount = null,
        onOrderClick = {}
    )
}