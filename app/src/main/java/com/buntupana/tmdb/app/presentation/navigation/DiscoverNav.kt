package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.app.presentation.nav3.RouteNav3
import com.buntupana.tmdb.feature.discover.presentation.discover.DiscoverScreen
import com.buntupana.tmdb.feature.discover.presentation.discover.DiscoverViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DiscoverNav(
    navigator: Navigator,
    viewModel: DiscoverViewModel = koinViewModel()
) {
    DiscoverScreen(
        viewModel = viewModel,
        onSearchClicked = {
            navigator.navigate(RouteNav3.Search)
        },
        onMediaItemClicked = { mediaItemType, mediaItemId, posterDominantColor ->
            navigator.navigate(
                RouteNav3.MediaDetail(
                    mediaId = mediaItemId,
                    mediaType = mediaItemType,
                    backgroundColor = posterDominantColor.toArgb()
                )
            )
        }
    )
}