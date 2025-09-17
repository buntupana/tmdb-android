package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import com.buntupana.tmdb.app.presentation.home.HomeRoute
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.detail.presentation.cast.CastDetailScreen
import com.buntupana.tmdb.feature.detail.presentation.person.PersonDetailRoute
import com.buntupana.tmdb.feature.search.presentation.SearchRoute

@Composable
fun CastDetailNav(
    navRoutesMain: NavRoutesMain
) {
    CastDetailScreen(
        onBackClick = { navRoutesMain.popBackStack() },
        onSearchClick = { navRoutesMain.navigate(SearchRoute) },
        onPersonClick = { personId ->
            navRoutesMain.navigate(
                PersonDetailRoute(personId = personId)
            )
        },
        onLogoClick = {
            navRoutesMain.popBackStack(HomeRoute::class)
        }
    )
}