package com.buntupana.tmdb.app.presentation.nav3

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.buntupana.tmdb.app.presentation.navigation.HomeNav
import com.buntupana.tmdb.app.presentation.navigation.SearchNav

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NavigationRootNav3(
    modifier: Modifier = Modifier
) {
    val navigationState = rememberNavigationState(
        startRoute = RouteNav3.Home
    )
    val navigator = remember {
        Navigator(navigationState)
    }
    val resultStore = rememberResultStore()
    NavDisplay(
        modifier = modifier
            .fillMaxSize(),
        onBack = navigator::goBack,
        sceneStrategy = rememberListDetailSceneStrategy(),
        entries = navigationState.toEntries(
            entryProvider {
                entry<RouteNav3.Home>(
//                        metadata = ListDetailScene.listPane()
                ) {
                    HomeNav(
                        navigator = navigator,
                        resultStore = resultStore
                    )
                }
                entry<RouteNav3.Search>{
                    SearchNav(
                        navigator = navigator
                    )
                }
            }
        )
    )
}