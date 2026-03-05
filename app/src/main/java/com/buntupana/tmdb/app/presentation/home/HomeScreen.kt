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
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.buntupana.tmdb.app.presentation.nav3.NavigationState
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.app.presentation.nav3.ResultStore
import com.buntupana.tmdb.app.presentation.nav3.RouteNav3
import com.buntupana.tmdb.app.presentation.nav3.rememberNavigationState
import com.buntupana.tmdb.app.presentation.nav3.toEntries
import com.buntupana.tmdb.app.presentation.navigation.AccountNav
import com.buntupana.tmdb.app.presentation.navigation.DiscoverNav
import com.buntupana.tmdb.app.presentation.navigation.MediaListNav
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.util.SetSystemBarsColors
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.panabuntu.tmdb.core.common.entity.MediaType


@Composable
fun HomeScreen(
    rootNavigator: Navigator,
    resultStore: ResultStore
) {
    HomeScreenContent(
        rootNavigator = rootNavigator,
        resultStore = resultStore
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HomeScreenContent(
    rootNavigator: Navigator,
    resultStore: ResultStore
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
    val bottomBarNavigator = remember {
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

                    val isSelected = item.route == bottomBarNavigator.state.topLevelRoute

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            bottomBarNavigator.navigate(item.route)
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
            onBack = bottomBarNavigator::goBack,
            sceneStrategy = rememberListDetailSceneStrategy(),
            entries = navigationState.toEntries(
                entryProvider {
                    entry<RouteNav3.Discover> {
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
                            navigator = rootNavigator
                        )
                    }
                }
            )
        )
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
            rootNavigator = getFakeNavigator(),
            resultStore = ResultStore(),
        )
    }
}


object DummyNavKey : NavKey

fun getFakeNavigator(): Navigator {
    return Navigator(
        NavigationState(
            DummyNavKey,
            mutableStateOf(DummyNavKey),
            mapOf()
        )
    )
}