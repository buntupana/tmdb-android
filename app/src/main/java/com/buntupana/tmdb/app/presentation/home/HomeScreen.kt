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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.buntupana.tmdb.app.R
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.util.setStatusNavigationBarColor
import com.buntupana.tmdb.feature.account.presentation.account.AccountNav
import com.buntupana.tmdb.feature.account.presentation.account.AccountScreen
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
    onMediaItemClicked: (mediaItemId: Long, mediaItemType: MediaType, posterDominantColor: Color) -> Unit
) {
    HomeScreenContent(
        onSignInClicked = onSignInClicked,
        onSearchClicked = onSearchClicked,
        onMediaItemClicked = onMediaItemClicked
    )
}

@Composable
fun HomeScreenContent(
    onSignInClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onMediaItemClicked: (mediaItemId: Long, mediaItemType: MediaType, posterDominantColor: Color) -> Unit
) {

    val navigationItems = listOf(
        BottomNavigationItem(
            title = stringResource(R.string.text_explore),
            selectedIcon = Icons.Rounded.Explore,
            unselectedIcon = Icons.Outlined.Explore,
            isSelected = true,
            route = DiscoverNav
        ),
        BottomNavigationItem(
            title = stringResource(R.string.text_movies),
            selectedIcon = Icons.Rounded.Movie,
            unselectedIcon = Icons.Outlined.Movie,
            isSelected = false,
            route = MoviesNav
        ),
        BottomNavigationItem(
            title = stringResource(R.string.text_tv_shows),
            selectedIcon = Icons.Rounded.Tv,
            unselectedIcon = Icons.Outlined.Tv,
            isSelected = false,
            route = TvShowsNav
        ),
        BottomNavigationItem(
            title = stringResource(R.string.text_account),
            selectedIcon = Icons.Rounded.Person,
            unselectedIcon = Icons.Outlined.Person,
            isSelected = false,
            route = AccountNav
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
                clickOnSearch = onSearchClicked
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
                AccountScreen(onSignInClicked = onSignInClicked)
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
        onMediaItemClicked = { _, _, _ -> }
    )
}