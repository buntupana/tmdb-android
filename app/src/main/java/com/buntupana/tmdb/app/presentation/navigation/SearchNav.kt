package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.feature.search.presentation.SearchScreen

@Composable
fun SearchNav(
    navigator: Navigator,
) {
    SearchScreen(
        onMediaClick = { mediaItemId, mediaItemType, posterDominantColor ->
//            navRoutesMain.navigate(
//                MediaDetailNavArgs(
//                    mediaId = mediaItemId,
//                    mediaType = mediaItemType,
//                    backgroundColor = posterDominantColor?.toArgb()
//                )
//            )
        },
        onPersonClick = { personId ->
//            navRoutesMain.navigate(
//                PersonDetailRoute(personId = personId)
//            )
        }
    )
}