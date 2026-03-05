package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.app.presentation.nav3.RouteNav3
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.feature.account.presentation.account.AccountScreen
import com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.ScreenType
import com.panabuntu.tmdb.core.common.entity.MediaType

@Composable
fun AccountNav(
    navigator: Navigator
) {
    AccountScreen(
        onSignInClick = {},
        onWatchListClick = { mediaType ->
            val mediaFilter = when (mediaType) {
                MediaType.MOVIE -> MediaFilter.MOVIES
                MediaType.TV_SHOW -> MediaFilter.TV_SHOWS
            }
            navigator.navigate(
                RouteNav3.WatchListFavorites(
                    screenType = ScreenType.WATCHLIST,
                    mediaFilterSelected = mediaFilter
                )
            )
        },
        onFavoritesClick = { mediaType ->
            val mediaFilter = when (mediaType) {
                MediaType.MOVIE -> MediaFilter.MOVIES
                MediaType.TV_SHOW -> MediaFilter.TV_SHOWS
            }
            navigator.navigate(
                RouteNav3.WatchListFavorites(
                    screenType = ScreenType.FAVORITES,
                    mediaFilterSelected = mediaFilter
                )
            )
        },
        onMediaItemClicked = { mediaItemType, mediaItemId, posterDominantColor ->
            navigator.navigate(
                RouteNav3.MediaDetail(
                    mediaId = mediaItemId,
                    mediaType = mediaItemType,
                    backgroundColor = posterDominantColor.toArgb()
                )
            )
        },
        onListsClick = {
            navigator.navigate(RouteNav3.Lists)
        },
        onListDetailClick = { listId, listName, description, backdropUrl ->
            navigator.navigate(
                RouteNav3.ListDetail(
                    listId = listId,
                    listName = listName,
                    description = description,
                    backdropUrl = backdropUrl
                )
            )
        }
    )
}