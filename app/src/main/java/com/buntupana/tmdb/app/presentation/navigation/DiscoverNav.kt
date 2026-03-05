package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.app.presentation.nav3.RouteNav3
import com.buntupana.tmdb.feature.discover.presentation.discover.DiscoverScreen

@Composable
fun DiscoverNav(
    navigator: Navigator
) {
    DiscoverScreen(
        onSearchClicked = {
            navigator.navigate(RouteNav3.Search)
        },
        onMediaItemClicked = { mediaItemType, mediaItemId, posterDominantColor ->

        }
    )
}