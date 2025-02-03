package com.buntupana.tmdb.feature.account.presentation.watchlist_favorites.comp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.buntupana.tmdb.core.ui.composables.LazyColumnGeneric
import com.buntupana.tmdb.core.ui.composables.item.MediaItemHorizontal
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.mediaItemMovie
import com.buntupana.tmdb.feature.account.presentation.R
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.flow.flowOf

@Composable
fun WatchlistPager(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    navigationBarPadding: Dp,
    pagingItems: LazyPagingItems<out MediaItem>?,
    noResultMessage: String,
    onMediaClick: (mediaItem: MediaItem, mainPosterColor: Color) -> Unit,
) {

    LazyColumnGeneric(
        modifier = Modifier.fillMaxSize(),
        state = state,
        topPadding = Dimens.padding.medium,
        bottomPadding = Dimens.padding.medium + navigationBarPadding,
        animateItem = true,
        itemList = pagingItems,
        noResultContent = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = noResultMessage
                )
            }
        }
    ) { item ->
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