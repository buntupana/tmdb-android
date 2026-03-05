package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.buntupana.tmdb.app.presentation.getViewModel
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.app.presentation.nav3.ResultStore
import com.buntupana.tmdb.app.presentation.nav3.RouteNav3
import com.buntupana.tmdb.feature.lists.presentation.delete_item_list.ListType
import com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.ScreenType
import com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.WatchListFavoritesNavArgs
import com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.WatchlistFavoritesScreen

@Composable
fun WatchlistFavoritesNav(
    navigator: Navigator,
    resultStore: ResultStore,
    route: RouteNav3.WatchListFavorites
) {

    val navArgs = WatchListFavoritesNavArgs(
        screenType = route.screenType,
        mediaFilterSelected = route.mediaFilterSelected
    )

//    val result = navRoutesMain.getStateFlowResult<DeleteItemResult>()
//        ?.collectAsStateWithLifecycle()?.value?.let {
//            WatchlistFavoritesResult.CancelRemoveItem(
//                mediaId = it.mediaId,
//                mediaType = it.mediaType
//            )
//        }

    WatchlistFavoritesScreen(
        viewModel = getViewModel(navArgs),
        watchlistFavoritesResult = null,
        onBackClick = { navigator.goBack() },
        onSearchClick = { navigator.navigate(RouteNav3.Search) },
        onMediaClick = { mediaId, mediaType, mainPosterColor ->
            navigator.navigate(
                RouteNav3.MediaDetail(
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

//            navRoutesMain.navigate(
//                DeleteItemListRoute(
//                    mediaId = mediaItem.id,
//                    mediaName = mediaItem.name,
//                    mediaType = mediaItem.mediaType,
//                    listType = listType
//                )
//            )
        }
    )
}