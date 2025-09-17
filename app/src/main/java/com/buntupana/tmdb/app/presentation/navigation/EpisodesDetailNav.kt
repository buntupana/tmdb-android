package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import com.buntupana.tmdb.app.presentation.home.HomeRoute
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.detail.presentation.episodes.EpisodesDetailScreen
import com.buntupana.tmdb.feature.detail.presentation.person.PersonDetailRoute
import com.buntupana.tmdb.feature.detail.presentation.rating.RatingMediaType
import com.buntupana.tmdb.feature.detail.presentation.rating.RatingRoute
import com.buntupana.tmdb.feature.search.presentation.SearchRoute

@Composable
fun EpisodesDetailNav(
    navRoutesMain: NavRoutesMain
) {
    EpisodesDetailScreen(
        onBackClick = { navRoutesMain.popBackStack() },
        onSearchClick = { navRoutesMain.navigate(SearchRoute) },
        onLogoClick = { navRoutesMain.popBackStack(HomeRoute::class) },
        onRateEpisodeClick = { tvShowId, episodeName: String, seasonNumber, episodeNumber, currentRating ->
            navRoutesMain.navigate(
                RatingRoute(
                    RatingMediaType.Episode(
                        tvShowId = tvShowId,
                        _title = episodeName,
                        seasonNumber = seasonNumber,
                        episodeNumber = episodeNumber,
                        _rating = currentRating
                    )
                )
            )
        },
        onPersonClick = { personId: Long ->
            navRoutesMain.navigate(
                PersonDetailRoute(personId = personId)
            )
        }
    )
}