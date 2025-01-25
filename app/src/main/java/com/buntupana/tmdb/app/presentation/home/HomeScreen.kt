package com.buntupana.tmdb.app.presentation.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
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
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.util.setStatusNavigationBarColor
import com.buntupana.tmdb.feature.account.presentation.account.account.AccountNav
import com.buntupana.tmdb.feature.account.presentation.account.account.AccountScreen
import com.buntupana.tmdb.feature.discover.presentation.comp.TopBar
import com.buntupana.tmdb.feature.discover.presentation.discover.DiscoverNav
import com.buntupana.tmdb.feature.discover.presentation.discover.DiscoverScreen
import com.buntupana.tmdb.feature.discover.presentation.movies.MoviesNav
import com.buntupana.tmdb.feature.discover.presentation.movies.MoviesScreen
import com.buntupana.tmdb.feature.discover.presentation.tv_shows.TvShowsNav
import com.buntupana.tmdb.feature.discover.presentation.tv_shows.TvShowsScreen
import com.panabuntu.tmdb.core.common.entity.MediaType


@Composable
fun HomeScreen(
    onSignInClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onWatchListClick: (mediaType: MediaType) -> Unit,
    onFavoritesClick: (mediaType: MediaType) -> Unit,
    onMediaItemClicked: (mediaItemId: Long, mediaItemType: MediaType, posterDominantColor: Color) -> Unit
) {
    HomeScreenContent(
        onSignInClicked = onSignInClicked,
        onSearchClicked = onSearchClicked,
        onWatchListClick = onWatchListClick,
        onFavoritesClick = onFavoritesClick,
        onMediaItemClicked = onMediaItemClicked
    )
}

@Composable
fun HomeScreenContent(
    onSignInClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onWatchListClick: (mediaType: MediaType) -> Unit,
    onFavoritesClick: (mediaType: MediaType) -> Unit,
    onMediaItemClicked: (mediaItemId: Long, mediaItemType: MediaType, posterDominantColor: Color) -> Unit
) {

    val navigationItems = listOf(
        TabNavigationItem.Discover(title = stringResource(R.string.text_explore)),
        TabNavigationItem.Movies(title = stringResource(R.string.text_movies)),
        TabNavigationItem.TVShows(title = stringResource(R.string.text_tv_shows)),
        TabNavigationItem.Account(title = stringResource(R.string.text_account))
    )

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.setStatusNavigationBarColor(),
        topBar = {
            TopBar(
                clickOnSearch = onSearchClicked
            )
        },
        bottomBar = {

            val currentBackStackEntry by navController.currentBackStackEntryAsState()

            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = PrimaryColor,
                contentColor = SecondaryColor
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
                                Icon(item.selectedIcon, contentDescription = item.title)
                            } else {
                                Icon(
                                    imageVector = item.unselectedIcon,
                                    contentDescription = item.title,
                                    tint = PrimaryColor.getOnBackgroundColor()
                                )
                            }
                        },
                        label = {
                            Text(
                                color = PrimaryColor.getOnBackgroundColor(),
                                text = item.title
                            )
                        }
                    )
                }
            }
        }
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = DiscoverNav
        ) {
            composable<DiscoverNav> {
                DiscoverScreen(onMediaItemClicked = onMediaItemClicked)
            }
            composable<MoviesNav> {
                MoviesScreen()
            }
            composable<TvShowsNav> {
                TvShowsScreen()
            }
            composable<AccountNav> {
                AccountScreen(
                    onSignInClick = onSignInClicked,
                    onWatchListClick = onWatchListClick,
                    onFavoritesClick = onFavoritesClick,
                    onMediaItemClicked = onMediaItemClicked,
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(
        onSignInClicked = {},
        onSearchClicked = {},
        onWatchListClick = {},
        onFavoritesClick = {},
        onMediaItemClicked = { _, _, _ -> }
    )
}