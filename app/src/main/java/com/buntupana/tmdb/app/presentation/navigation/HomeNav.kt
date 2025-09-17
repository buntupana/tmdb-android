package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.buntupana.tmdb.app.presentation.home.HomeScreen
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.account.presentation.sign_in.SignInRoute
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailRoute
import com.buntupana.tmdb.feature.discover.presentation.media_filter.MediaFilterRoute
import com.buntupana.tmdb.feature.discover.presentation.media_list.MediaListResult
import com.buntupana.tmdb.feature.lists.presentation.list_detail.ListDetailRoute
import com.buntupana.tmdb.feature.lists.presentation.lists.ListsRoute
import com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.ScreenType
import com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.WatchListFavoritesRoute
import com.buntupana.tmdb.feature.search.presentation.SearchRoute
import com.panabuntu.tmdb.core.common.entity.MediaType

@Composable
fun HomeNav(
    navRoutesMain: NavRoutesMain,
) {
    HomeScreen(
        mediaListResult = navRoutesMain.getResult<MediaListResult>(),
        onSignInClicked = {
            navRoutesMain.navigate(SignInRoute())
        },
        onSearchClicked = {
            navRoutesMain.navigate(SearchRoute)
        },
        onMediaItemClicked = { mediaItemType, mediaItemId, posterDominantColor ->
            navRoutesMain.navigate(
                MediaDetailRoute(
                    mediaId = mediaItemId,
                    mediaType = mediaItemType,
                    backgroundColor = posterDominantColor.toArgb()
                )
            )
        },
        onWatchListClick = { mediaType ->
            val mediaFilter = when (mediaType) {
                MediaType.MOVIE -> MediaFilter.MOVIES
                MediaType.TV_SHOW -> MediaFilter.TV_SHOWS
            }
            navRoutesMain.navigate(
                WatchListFavoritesRoute(
                    ScreenType.WATCHLIST,
                    mediaFilter
                )
            )
        },
        onFavoritesClick = { mediaType ->
            val mediaFilter = when (mediaType) {
                MediaType.MOVIE -> MediaFilter.MOVIES
                MediaType.TV_SHOW -> MediaFilter.TV_SHOWS
            }
            navRoutesMain.navigate(
                WatchListFavoritesRoute(
                    ScreenType.FAVORITES,
                    mediaFilter
                )
            )
        },
        onListsClick = {
            navRoutesMain.navigate(ListsRoute)
        },
        onListDetailClick = { listId, listName, description, backdropUrl ->
            navRoutesMain.navigate(
                ListDetailRoute(
                    listId = listId,
                    listName = listName,
                    description = description,
                    backdropUrl = backdropUrl
                )
            )
        },
        onMovieFilterClick = { mediaListFilter ->
            navRoutesMain.navigate(
                MediaFilterRoute(
                    mediaListFilter = mediaListFilter
                )
            )
        }
    )
}