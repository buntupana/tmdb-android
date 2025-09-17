package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailRoute
import com.buntupana.tmdb.feature.lists.presentation.delete_item_list.DeleteItemListRoute
import com.buntupana.tmdb.feature.lists.presentation.delete_item_list.DeleteItemResult
import com.buntupana.tmdb.feature.lists.presentation.delete_item_list.ListType
import com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.ScreenType
import com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.WatchlistFavoritesResult
import com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.WatchlistFavoritesScreen
import com.buntupana.tmdb.feature.search.presentation.SearchRoute

@Composable
fun WatchlistFavoritesNav(
    navRoutesMain: NavRoutesMain
) {
    val result = navRoutesMain.getStateFlowResult<DeleteItemResult>()
        ?.collectAsStateWithLifecycle()?.value?.let {
            WatchlistFavoritesResult.CancelRemoveItem(
                mediaId = it.mediaId,
                mediaType = it.mediaType
            )
        }

    WatchlistFavoritesScreen(
        watchlistFavoritesResult = result,
        onBackClick = { navRoutesMain.popBackStack() },
        onSearchClick = { navRoutesMain.navigate(SearchRoute) },
        onMediaClick = { mediaId, mediaType, mainPosterColor ->
            navRoutesMain.navigate(
                MediaDetailRoute(
                    mediaId = mediaId,
                    mediaType = mediaType,
                    backgroundColor = mainPosterColor?.toArgb()
                )
            )
        },
        onDeleteClick = { itemId, mediaItem, screenType ->

            val listType = when (screenType) {
                ScreenType.WATCHLIST -> ListType.Watchlist
                ScreenType.FAVORITES -> ListType.Favorites
            }

            navRoutesMain.navigate(
                DeleteItemListRoute(
                    mediaId = mediaItem.id,
                    mediaName = mediaItem.name,
                    mediaType = mediaItem.mediaType,
                    listType = listType
                )
            )
        }
    )
}