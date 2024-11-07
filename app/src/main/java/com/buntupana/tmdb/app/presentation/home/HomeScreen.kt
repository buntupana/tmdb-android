package com.buntupana.tmdb.app.presentation.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.buntupana.tmdb.app.R
import com.buntupana.tmdb.app.presentation.navigation.CommonNavigation
import com.buntupana.tmdb.app.presentation.navigation.NavRoutesMainImpl
import com.buntupana.tmdb.app.presentation.navigation.Routes
import com.buntupana.tmdb.core.presentation.theme.PrimaryColor
import com.buntupana.tmdb.core.presentation.theme.SecondaryColor
import com.buntupana.tmdb.core.presentation.util.getOnBackgroundColor
import com.buntupana.tmdb.core.presentation.util.setStatusNavigationBarColor
import com.buntupana.tmdb.feature.discover.presentation.comp.TopBar
import com.buntupana.tmdb.feature.discover.presentation.discover.DiscoverNavigator
import com.buntupana.tmdb.feature.discover.presentation.discover.DiscoverScreen
import com.buntupana.tmdb.feature.discover.presentation.movies.MoviesScreen
import com.buntupana.tmdb.feature.discover.presentation.tv_shows.TvShowsScreen


@Composable
fun HomeScreen(
    discoverNavigator: DiscoverNavigator
) {
    HomeScreenContent(
        discoverNavigator,
        navigateToSearch = { discoverNavigator.navigateToSearch() }
    )
}

@Composable
fun HomeScreenContent(
    discoverNavigator: DiscoverNavigator,
    navigateToSearch: () -> Unit,
) {

    val navigationItems = listOf(
        BottomNavigationItem(
            title = stringResource(R.string.text_explore),
            selectedIcon = Icons.Rounded.Explore,
            unselectedIcon = Icons.Outlined.Explore,
            isSelected = true,
            route = Routes.Discover
        ),
        BottomNavigationItem(
            title = stringResource(R.string.text_movies),
            selectedIcon = Icons.Rounded.Movie,
            unselectedIcon = Icons.Outlined.Movie,
            isSelected = false,
            route = Routes.Movies
        ),
        BottomNavigationItem(
            title = stringResource(R.string.text_tv_shows),
            selectedIcon = Icons.Rounded.Tv,
            unselectedIcon = Icons.Outlined.Tv,
            isSelected = false,
            route = Routes.TvShows
        ),
        BottomNavigationItem(
            title = stringResource(R.string.text_account),
            selectedIcon = Icons.Rounded.Person,
            unselectedIcon = Icons.Outlined.Person,
            isSelected = false,
            route = Routes.TvShows
        )
    )

    var selectedNavigationIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.setStatusNavigationBarColor(),
        topBar = {
            TopBar(
                clickOnSearch = navigateToSearch
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = PrimaryColor,
                contentColor = SecondaryColor
            ) {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == selectedNavigationIndex,
                        onClick = {
                            selectedNavigationIndex = index
                            navController.navigate(item.route)
                        },
                        icon = {
                            if (index == selectedNavigationIndex) {
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
            startDestination = Routes.Discover
        ) {
            composable<Routes.Discover> {
                DiscoverScreen(discoverNavigator = discoverNavigator)
            }
            composable<Routes.Movies> {
                MoviesScreen()
            }
            composable<Routes.TvShows> {
                TvShowsScreen()
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(
        discoverNavigator = CommonNavigation(NavRoutesMainImpl()),
        navigateToSearch = {}
    )
}