package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailRoute
import com.buntupana.tmdb.feature.detail.presentation.person.PersonDetailRoute
import com.buntupana.tmdb.feature.search.presentation.SearchScreen

@Composable
fun SearchNav(navRoutesMain: NavRoutesMain) {
    SearchScreen(
        onMediaClick = { mediaItemId, mediaItemType, posterDominantColor ->
            navRoutesMain.navigate(
                MediaDetailRoute(
                    mediaId = mediaItemId,
                    mediaType = mediaItemType,
                    backgroundColor = posterDominantColor?.toArgb()
                )
            )
        },
        onPersonClick = { personId ->
            navRoutesMain.navigate(
                PersonDetailRoute(personId = personId)
            )
        }
    )
}