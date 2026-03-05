package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.feature.account.presentation.account.AccountScreen

@Composable
fun AccountNav(
    navigator: Navigator
) {
    AccountScreen(
        onSignInClick = {},
        onWatchListClick = {},
        onFavoritesClick = { mediaType ->

        },
        onMediaItemClicked = { mediaItemType, mediaItemId, posterDominantColor ->

        },
        onListsClick = { },
        onListDetailClick = { listId, listName, description, backdropUrl ->

        }
    )
}