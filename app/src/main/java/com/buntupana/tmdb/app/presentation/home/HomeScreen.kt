package com.buntupana.tmdb.app.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.buntupana.tmdb.app.R
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.util.SetSystemBarsColors
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.account.presentation.account.AccountNav
import com.buntupana.tmdb.feature.account.presentation.account.AccountScreen
import com.buntupana.tmdb.feature.discover.presentation.discover.DiscoverNav
import com.buntupana.tmdb.feature.discover.presentation.discover.DiscoverScreen
import com.buntupana.tmdb.feature.discover.presentation.media_list.MediaListNav
import com.buntupana.tmdb.feature.discover.presentation.media_list.MediaListScreen
import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter
import com.panabuntu.tmdb.core.common.entity.MediaType


@Composable
fun HomeScreen(
    mediaListFilterResult: MediaListFilter?,
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
        mediaListFilterResult = mediaListFilterResult,
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

@Composable
fun HomeScreenContent(
    mediaListFilterResult: MediaListFilter?,
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
        TabNavigationItem.Discover(title = stringResource(R.string.text_explore)),
        TabNavigationItem.Movies(title = stringResource(R.string.text_movies)),
        TabNavigationItem.TVShows(title = stringResource(R.string.text_tv_shows)),
        TabNavigationItem.Account(title = stringResource(R.string.text_account))
    )

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {

            val currentBackStackEntry by navController.currentBackStackEntryAsState()

            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                navigationItems.forEach { item ->

                    val isSelected =
                        currentBackStackEntry?.destination?.hasRoute(item.route::class) ?: false

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
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

        NavHost(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            navController = navController,
            startDestination = DiscoverNav
        ) {
            composable<DiscoverNav> {
                DiscoverScreen(
                    onSearchClicked = onSearchClicked,
                    onMediaItemClicked = onMediaItemClicked
                )
            }
            composable<MediaListNav.Movie> {
                MediaListScreen(
                    mediaListFilterResult = mediaListFilterResult,
                    onMediaItemClicked = onMediaItemClicked,
                    onFilterClick = onMovieFilterClick
                )
            }
            composable<MediaListNav.TvShow> {
                MediaListScreen(
                    mediaListFilterResult = mediaListFilterResult,
                    onMediaItemClicked = onMediaItemClicked,
                    onFilterClick = onMovieFilterClick
                )
            }
            composable<AccountNav> {
                AccountScreen(
                    onSignInClick = onSignInClicked,
                    onWatchListClick = onWatchListClick,
                    onFavoritesClick = onFavoritesClick,
                    onMediaItemClicked = onMediaItemClicked,
                    onListsClick = onListsClick,
                    onListDetailClick = onListDetailClick
                )
            }
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
fun HomeScreenPreview() {
    AppTheme {
        HomeScreenContent(
            mediaListFilterResult = null,
            onSignInClicked = {},
            onSearchClicked = {},
            onWatchListClick = {},
            onFavoritesClick = {},
            onListsClick = {},
            onMediaItemClicked = { _, _, _ -> },
            onMovieFilterClick = {},
            onListDetailClick = { _, _, _, _ -> }
        )
    }
}