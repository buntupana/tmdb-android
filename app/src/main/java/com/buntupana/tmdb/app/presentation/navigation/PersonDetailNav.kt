package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.buntupana.tmdb.app.presentation.home.HomeRoute
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailRoute
import com.buntupana.tmdb.feature.detail.presentation.person.PersonDetailScreen
import com.buntupana.tmdb.feature.search.presentation.SearchRoute

@Composable
fun PersonDetailNav(
    navRoutesMain: NavRoutesMain,
) {
    PersonDetailScreen(
        onBackClick = { navRoutesMain.popBackStack() },
        onSearchClick = { navRoutesMain.navigate(SearchRoute) },
        onMediaClick = { mediaItemId, mediaItemType, posterDominantColor ->
            navRoutesMain.navigate(
                MediaDetailRoute(
                    mediaId = mediaItemId,
                    mediaType = mediaItemType,
                    backgroundColor = posterDominantColor?.toArgb()
                )
            )
        },
        onLogoClick = {
            navRoutesMain.popBackStack(HomeRoute::class)
        }
    )
}