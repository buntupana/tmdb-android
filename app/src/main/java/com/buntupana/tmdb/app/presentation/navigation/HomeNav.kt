package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import com.buntupana.tmdb.app.presentation.home.HomeScreen
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.app.presentation.nav3.ResultStore
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.feature.discover.presentation.media_list.MediaListResult
import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter
import com.panabuntu.tmdb.core.common.entity.MediaType

@Composable
fun HomeNav(
    navigator: Navigator,
    resultStore: ResultStore
) {

//    val mediaListResult = navRoutesMain.getResult<MediaFilterResult>()?.let { result ->
//        MediaListResult.ApplyFilter(result.mediaListFilter)
//    }

    val mediaListResult: MediaListResult = MediaListResult.ApplyFilter(
        MediaListFilter.Movie()
    )

    HomeScreen(
        rootNavigator = navigator,
        resultStore = resultStore,
        mediaListResult = mediaListResult,
        onSignInClicked = {
//            navRoutesMain.navigate(SignInRoute())
        },
        onSearchClicked = {
//            navRoutesMain.navigate(SearchRoute)
        },
        onMediaItemClicked = { mediaItemType, mediaItemId, posterDominantColor ->
//            navRoutesMain.navigate(
//                MediaDetailRoute(
//                    mediaId = mediaItemId,
//                    mediaType = mediaItemType,
//                    backgroundColor = posterDominantColor.toArgb()
//                )
//            )
        },
        onWatchListClick = { mediaType ->
            val mediaFilter = when (mediaType) {
                MediaType.MOVIE -> MediaFilter.MOVIES
                MediaType.TV_SHOW -> MediaFilter.TV_SHOWS
            }
//            navRoutesMain.navigate(
//                WatchListFavoritesRoute(
//                    ScreenType.WATCHLIST,
//                    mediaFilter
//                )
//            )
        },
        onFavoritesClick = { mediaType ->
            val mediaFilter = when (mediaType) {
                MediaType.MOVIE -> MediaFilter.MOVIES
                MediaType.TV_SHOW -> MediaFilter.TV_SHOWS
            }
//            navRoutesMain.navigate(
//                WatchListFavoritesRoute(
//                    ScreenType.FAVORITES,
//                    mediaFilter
//                )
//            )
        },
        onListsClick = {
//            navRoutesMain.navigate(ListsRoute)
        },
        onListDetailClick = { listId, listName, description, backdropUrl ->
//            navRoutesMain.navigate(
//                ListDetailRoute(
//                    listId = listId,
//                    listName = listName,
//                    description = description,
//                    backdropUrl = backdropUrl
//                )
//            )
        },
        onMovieFilterClick = { mediaListFilter ->
//            navRoutesMain.navigate(
//                MediaFilterRoute(
//                    mediaListFilter = mediaListFilter
//                )
//            )
        }
    )
}