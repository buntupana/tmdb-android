package com.buntupana.tmdb.feature.account.presentation.account.watchlist

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.buntupana.tmdb.core.ui.composables.item.MediaItemHorizontal
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.panabuntu.tmdb.core.common.model.MediaItem

@Composable
fun WatchlistPager(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<out MediaItem>?,
    noResultMessage: String,
    onMediaClick: (mediaItem: MediaItem, mainPosterColor: Color) -> Unit,
) {
    if (pagingItems == null) {
        return
    }

    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = listState
    ) {

        // Setting result items
        item {
            Spacer(modifier = Modifier.height(Dimens.padding.medium))
        }

        // Drawing result items
        items(pagingItems.itemCount) { index ->

            val item = pagingItems[index] ?: return@items

            Box(
                modifier = Modifier.animateItem()
            ) {
                MediaItemHorizontal(
                    modifier = modifier
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
        }

        when (pagingItems.loadState.refresh) {
            LoadState.Loading -> {
                if (pagingItems.itemCount == 0){
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
                // TODO: Error to show when first page of paging fails
            }

            is LoadState.NotLoading -> {
                if (pagingItems.itemCount == 0) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Dimens.padding.medium)
                        ) {
                            Text(text = noResultMessage)
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
                // TODO: item to show when append paging fails
            }

            else -> {}
        }
        item {
            Spacer(modifier = Modifier.height(Dimens.padding.medium))
        }
    }
}