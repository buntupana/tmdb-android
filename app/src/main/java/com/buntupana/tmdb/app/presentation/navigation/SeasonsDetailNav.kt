package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.buntupana.tmdb.app.presentation.home.HomeRoute
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.detail.presentation.episodes.EpisodesDetailRoute
import com.buntupana.tmdb.feature.detail.presentation.seasons.SeasonsDetailScreen
import com.buntupana.tmdb.feature.search.presentation.SearchRoute

@Composable
fun SeasonsDetailNav(
    navRoutesMain: NavRoutesMain
) {
    SeasonsDetailScreen(
        onBackClick = { navRoutesMain.popBackStack() },
        onSearchClick = { navRoutesMain.navigate(SearchRoute) },
        onSeasonClick = { tvShowId, seasonName, seasonNumber, posterUrl, backgroundColor, releaseYear ->
            navRoutesMain.navigate(
                EpisodesDetailRoute(
                    tvShowId = tvShowId,
                    seasonName = seasonName,
                    seasonNumber = seasonNumber,
                    posterUrl = posterUrl,
                    backgroundColor = backgroundColor.toArgb(),
                    releaseYear = releaseYear
                )
            )
        },
        onLogoClick = {
            navRoutesMain.popBackStack(HomeRoute::class)
        }
    )
}