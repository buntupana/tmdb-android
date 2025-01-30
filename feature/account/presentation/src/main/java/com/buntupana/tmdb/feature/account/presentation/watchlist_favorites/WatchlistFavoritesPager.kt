package com.buntupana.tmdb.feature.account.presentation.watchlist_favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.buntupana.tmdb.core.ui.composables.ErrorAndRetry
import com.buntupana.tmdb.core.ui.composables.item.MediaItemHorizontal
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.util.mediaItemMovie
import com.buntupana.tmdb.feature.account.presentation.R
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.flow.flowOf
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun WatchlistPager(
    modifier: Modifier = Modifier,
    navigationBarPadding: Dp,
    pagingItems: LazyPagingItems<out MediaItem>?,
    noResultMessage: String,
    onMediaClick: (mediaItem: MediaItem, mainPosterColor: Color) -> Unit,
) {
    if (pagingItems == null) {
        return
    }

    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {

        // Setting result items
        item {
            Spacer(modifier = Modifier.height(Dimens.padding.medium))
        }

        // Drawing result items
        items(pagingItems.itemCount, key = { index -> pagingItems[index]?.id ?: index }) { index ->

            val item = pagingItems[index] ?: return@items

            MediaItemHorizontal(
                modifier = modifier
                    .animateItem()
                    .height(Dimens.imageSize.posterHeight),
                onMediaClick = { _, mainPosterColor ->
                    onMediaClick(item, mainPosterColor)
                },
                mediaId = item.id,
                title = item.name,
                posterUrl = item.posterUrl,
                overview = item.overview.orEmpty(),
                releaseDate = item.releaseDate
            )
        }

        when (pagingItems.loadState.refresh) {
            LoadState.Loading -> {
                if (pagingItems.itemCount == 0) {
                    item {
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(Dimens.padding.medium))
                            CircularProgressIndicator(color = PrimaryColor)
                        }
                    }
                }
            }

            is LoadState.Error -> {
                item {
                    ErrorAndRetry(
                        modifier = Modifier.fillMaxWidth(),
                        textColor = MaterialTheme.colorScheme.background.getOnBackgroundColor(),
                        errorMessage = stringResource(RCore.string.message_loading_content_error),
                        onRetryClick = { pagingItems.retry() }
                    )
                }
            }

            is LoadState.NotLoading -> {
                if (pagingItems.itemCount == 0) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Dimens.padding.medium)
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = noResultMessage
                            )
                        }
                    }
                    return@LazyColumn
                }
            }
        }
        // Appending result strategy
        when (pagingItems.loadState.append) {
            LoadState.Loading -> {
                item {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(Dimens.padding.medium))
                        CircularProgressIndicator(
                            color = PrimaryColor
                        )
                    }
                }
            }

            is LoadState.Error -> {
                item {
                    ErrorAndRetry(
                        modifier = Modifier.fillMaxWidth(),
                        textColor = MaterialTheme.colorScheme.background.getOnBackgroundColor(),
                        errorMessage = stringResource(RCore.string.message_loading_content_error),
                        onRetryClick = { pagingItems.retry() }
                    )
                }
            }

            else -> {}
        }
        item {
            Spacer(modifier = Modifier.height(Dimens.padding.medium + navigationBarPadding))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WatchlistPagerPreview() {

    val itemsList = PagingData.from(
        data = listOf(mediaItemMovie, mediaItemMovie),
        sourceLoadStates = LoadStates(
            refresh = LoadState.NotLoading(true),
            prepend = LoadState.NotLoading(true),
            append = LoadState.Error(Throwable())
        )
    )

    WatchlistPager(
        modifier = Modifier.fillMaxSize(),
        pagingItems = flowOf(itemsList).collectAsLazyPagingItems(),
        navigationBarPadding = 0.dp,
        noResultMessage = stringResource(
            R.string.message_no_results,
            "movies",
            "favorites"
        ),
        onMediaClick = { _, _ -> }
    )
}