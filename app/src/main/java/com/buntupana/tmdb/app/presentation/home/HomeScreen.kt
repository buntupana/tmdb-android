package com.buntupana.tmdb.app.presentation.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.app.presentation.nav3.ResultStore
import com.buntupana.tmdb.app.presentation.nav3.RouteNav3
import com.buntupana.tmdb.app.presentation.nav3.rememberNavigationState
import com.buntupana.tmdb.app.presentation.nav3.toEntries
import com.buntupana.tmdb.app.presentation.navigation.AccountNav
import com.buntupana.tmdb.app.presentation.navigation.DiscoverNav
import com.buntupana.tmdb.app.presentation.navigation.MediaListNav
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.util.SetSystemBarsColors
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.discover.presentation.media_list.MediaListResult
import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter
import com.panabuntu.tmdb.core.common.entity.MediaType


@Composable
fun HomeScreen(
    rootNavigator: Navigator,
    resultStore: ResultStore,
    mediaListResult: MediaListResult?,
    onSignInClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onWatchListClick: (mediaType: MediaType) -> Unit,
    onFavoritesClick: (mediaType: MediaType) -> Unit,
    onListsClick: () -> Unit,
    onMediaItemClicked: (mediaItemType: MediaType, mediaItemId: Long, posterDominantColor: Color) -> Unit,
    onListDetailClick: (listId: Long, listName: String, description: String?, backdropUrl: String?) -> Unit,
    onMovieFilterClick: (mediaListFilter: MediaListFilter) -> Unit
) {
    HomeScreenContent(
        rootNavigator = rootNavigator,
        resultStore = resultStore,
        mediaListResult = mediaListResult,
        onSignInClicked = onSignInClicked,
        onSearchClicked = onSearchClicked,
        onWatchListClick = onWatchListClick,
        onFavoritesClick = onFavoritesClick,
        onListsClick = onListsClick,
        onMediaItemClicked = onMediaItemClicked,
        onMovieFilterClick = onMovieFilterClick,
        onListDetailClick = onListDetailClick
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HomeScreenContent(
    rootNavigator: Navigator,
    resultStore: ResultStore,
    mediaListResult: MediaListResult?,
    onSignInClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onWatchListClick: (mediaType: MediaType) -> Unit,
    onFavoritesClick: (mediaType: MediaType) -> Unit,
    onListsClick: () -> Unit,
    onMediaItemClicked: (mediaItemType: MediaType, mediaItemId: Long, posterDominantColor: Color) -> Unit,
    onListDetailClick: (listId: Long, listName: String, description: String?, backdropUrl: String?) -> Unit,
    onMovieFilterClick: (movieListFilter: MediaListFilter) -> Unit
) {

    SetSystemBarsColors(
        navigationBarColor = MaterialTheme.colorScheme.primaryContainer,
        translucentNavigationBar = false
    )

    val navigationItems = listOf(
        TabNavigationItem.Discover(title = stringResource(R.string.common_explore)),
        TabNavigationItem.Movies(title = stringResource(R.string.common_movies)),
        TabNavigationItem.TVShows(title = stringResource(R.string.common_tv_shows)),
        TabNavigationItem.Account(title = stringResource(R.string.common_account))
    )

    val navigationState = rememberNavigationState(
        startRoute = RouteNav3.Discover,
        topLevelRoutes = setOf(
            RouteNav3.Discover,
            RouteNav3.Account,
            RouteNav3.MediaList.Movie(),
            RouteNav3.MediaList.TvShow()
        )
    )
    val navigator = remember {
        Navigator(navigationState)
    }

    Scaffold(
        bottomBar = {

            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                navigationItems.forEach { item ->

                    val isSelected = item.route == navigator.state.topLevelRoute

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navigator.navigate(item.route)
                        },
                        icon = {
                            if (isSelected) {
                                Icon(
                                    item.selectedIcon,
                                    contentDescription = item.title,
                                )
                            } else {
                                Icon(
                                    imageVector = item.unselectedIcon,
                                    contentDescription = item.title,
                                    tint = MaterialTheme.colorScheme.primaryContainer.getOnBackgroundColor()
                                )
                            }
                        },
                        label = {
                            Text(
                                color = MaterialTheme.colorScheme.primaryContainer.getOnBackgroundColor(),
                                text = item.title
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->

        NavDisplay(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            onBack = navigator::goBack,
            sceneStrategy = rememberListDetailSceneStrategy(),
            entries = navigationState.toEntries(
                entryProvider {
                    entry<RouteNav3.Discover>(
//                        metadata = ListDetailScene.listPane()
                    ) {
                        DiscoverNav(
                            navigator = rootNavigator
                        )
                    }

                    entry<RouteNav3.MediaList.Movie> {
                        MediaListNav(
                            navigator = rootNavigator,
                            resultStore = resultStore,
                            mediaType = MediaType.MOVIE
                        )
                    }

                    entry<RouteNav3.MediaList.TvShow> {
                        MediaListNav(
                            navigator = rootNavigator,
                            resultStore = resultStore,
                            mediaType = MediaType.TV_SHOW
                        )
                    }

                    entry<RouteNav3.Account> {
                        AccountNav(
                            navigator = navigator
                        )
                    }
                }
            )
        )
    }
}

//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_NO,
//    name = "DefaultPreviewLight",
//    showBackground = true,
//)
//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    name = "DefaultPreviewDark",
//    showBackground = true,
//)
//@Composable
//fun HomeScreenPreview() {
//    AppTheme {
//        HomeScreenContent(
//            rootNavigator = Navigator(NavigationState()),
//            resultStore = null,
//            mediaListResult = null,
//            onSignInClicked = {},
//            onSearchClicked = {},
//            onWatchListClick = {},
//            onFavoritesClick = {},
//            onListsClick = {},
//            onMediaItemClicked = { _, _, _ -> },
//            onMovieFilterClick = {},
//            onListDetailClick = { _, _, _, _ -> }
//        )
//    }
//}