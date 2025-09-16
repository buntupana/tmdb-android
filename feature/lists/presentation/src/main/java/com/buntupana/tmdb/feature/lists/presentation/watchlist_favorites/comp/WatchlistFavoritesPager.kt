package com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.comp

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.buntupana.tmdb.core.ui.composables.item.ActionsAlign
import com.buntupana.tmdb.core.ui.composables.item.MediaItemHorizontal
import com.buntupana.tmdb.core.ui.composables.item.SwipeableItem
import com.buntupana.tmdb.core.ui.composables.list.LazyColumnGeneric
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.MediaItemRevealedViewEntity
import com.buntupana.tmdb.core.ui.util.mediaItemMovie
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.flow.flowOf

@Composable
fun WatchlistFavoritePager(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    navigationBarPadding: Dp,
    pagingItems: LazyPagingItems<out MediaItemRevealedViewEntity>?,
    noResultMessage: String,
    onMediaClick: (mediaItem: MediaItem, mainPosterColor: Color) -> Unit,
    onItemDeleteClick: (id: String, mediaItem: MediaItem) -> Unit
) {

    LazyColumnGeneric(
        modifier = modifier.fillMaxSize(),
        state = state,
        topPadding = Dimens.padding.medium,
        bottomPadding = { Dimens.padding.medium + navigationBarPadding },
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
    ) { _, item ->

        val isRevealed = item.isDeleteRevealed

        // key is need it to recomposite when isRevealed
        key(isRevealed) {
            SwipeableItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                actionsAlign = ActionsAlign.END,
                onExpanded = {
                    onItemDeleteClick(item.id, item.mediaItem)
                    item.isDeleteRevealed.value = true
                },
                isRevealed = isRevealed.value,
                actions = { progressProvider ->

                    val backgroundColor = animateColorAsState(
                        targetValue = lerp(
                            start = MaterialTheme.colorScheme.background,
                            stop = MaterialTheme.colorScheme.error,
                            fraction = progressProvider() * 2
                        ),
                        animationSpec = tween(0)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                horizontal = Dimens.padding.horizontal,
                                vertical = Dimens.padding.verticalItem
                            )
                            .clip(RoundedCornerShape(Dimens.posterRound))
                            .background(backgroundColor.value),
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(horizontal = Dimens.padding.huge),
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onError,
                        )
                    }
                },
                content = {
                    MediaItemHorizontal(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                        mediaId = item.mediaItem.id,
                        title = item.mediaItem.name,
                        posterUrl = item.mediaItem.posterUrl,
                        overview = item.mediaItem.overview,
                        releaseDate = item.mediaItem.releaseDate,
                        onMediaClick = { _, mainPosterColor ->
                            onMediaClick(item.mediaItem, mainPosterColor)
                        }
                    )
                }
            )
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
private fun WatchlistPagerPreview() {

    val itemsList = PagingData.from(
        data = listOf(
            MediaItemRevealedViewEntity(id = 0.toString(), mediaItem = mediaItemMovie),
            MediaItemRevealedViewEntity(id = 1.toString(), mediaItem = mediaItemMovie),
        ),
        sourceLoadStates = LoadStates(
            refresh = LoadState.NotLoading(true),
            prepend = LoadState.NotLoading(true),
            append = LoadState.Error(Throwable())
        )
    )

    AppTheme {
        WatchlistFavoritePager(
            modifier = Modifier.fillMaxSize(),
            pagingItems = flowOf(itemsList).collectAsLazyPagingItems(),
            navigationBarPadding = 0.dp,
            noResultMessage = stringResource(
                R.string.message_no_results_in,
                "movies",
                "favorites"
            ),
            onMediaClick = { _, _ -> },
            onItemDeleteClick = { _, _ -> }
        )
    }
}