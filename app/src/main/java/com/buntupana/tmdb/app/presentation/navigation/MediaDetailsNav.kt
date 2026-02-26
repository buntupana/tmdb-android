package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.buntupana.tmdb.app.presentation.home.HomeRoute
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.feature.detail.presentation.cast.CastDetailRoute
import com.buntupana.tmdb.feature.detail.presentation.episodes.EpisodesDetailRoute
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailRoute
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailScreen
import com.buntupana.tmdb.feature.detail.presentation.person.PersonDetailRoute
import com.buntupana.tmdb.feature.detail.presentation.rating.RatingMediaType
import com.buntupana.tmdb.feature.detail.presentation.rating.RatingRoute
import com.buntupana.tmdb.feature.detail.presentation.seasons.SeasonsDetailRoute
import com.buntupana.tmdb.feature.lists.presentation.manage_lists.ManageListsRoute
import com.buntupana.tmdb.feature.search.presentation.SearchRoute
import com.panabuntu.tmdb.core.common.entity.MediaType

@Composable
fun MediaDetailsNav(
    navRoutesMain: NavRoutesMain
) {
    MediaDetailScreen(
        onBackClick = navRoutesMain::popBackStack,
        onSearchClick = { navRoutesMain.navigate(SearchRoute) },
        onPersonClick = { personId ->
            navRoutesMain.navigate(
                PersonDetailRoute(personId = personId)
            )
        },
        onFullCastClick = { mediaId, mediaType, mediaTitle, mediaReleaseYear, mediaPosterUrl, backgroundColor ->
            navRoutesMain.navigate(
                CastDetailRoute(
                    mediaId = mediaId,
                    mediaType = mediaType,
                    mediaTitle = mediaTitle,
                    releaseYear = mediaReleaseYear,
                    posterUrl = mediaPosterUrl,
                    backgroundColor = backgroundColor.toArgb()
                )
            )
        },
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
        onAllSeasonsClick = { tvShowId, tvShowTitle, releaseYear, posterUrl, backgroundColor ->
            navRoutesMain.navigate(
                SeasonsDetailRoute(
                    tvShowId = tvShowId,
                    tvShowName = tvShowTitle,
                    releaseYear = releaseYear,
                    posterUrl = posterUrl,
                    backgroundColor = backgroundColor.toArgb()
                )
            )
        },
        onRecommendationClick = { mediaId, mediaType, backgroundColor ->
            navRoutesMain.navigate(
                MediaDetailRoute(
                    mediaId = mediaId,
                    mediaType = mediaType,
                    backgroundColor = backgroundColor?.toArgb()
                )
            )
        },
        onLogoClick = {
            navRoutesMain.popBackStack(route = HomeRoute::class, inclusive = false)
        },
        onRatingClick = { mediaId, mediaType, mediaTitle, rating ->

            when (mediaType) {
                MediaType.MOVIE -> {
                    RatingMediaType.Movie(
                        movieId = mediaId,
                        _title = mediaTitle,
                        _rating = rating
                    )
                }

                MediaType.TV_SHOW -> {
                    RatingMediaType.TvShow(
                        tvShowId = mediaId,
                        _title = mediaTitle,
                        _rating = rating
                    )
                }
            }.let {
                navRoutesMain.navigate(
                    RatingRoute(it)
                )
            }

        },
        onManageListClick = { mediaId, mediaType, mediaName, mediaPosterUrl, backgroundColor, releaseYear ->
            navRoutesMain.navigate(
                ManageListsRoute(
                    mediaId = mediaId,
                    mediaType = mediaType,
                    posterUrl = mediaPosterUrl,
                    mediaName = mediaName,
                    backgroundColor = backgroundColor,
                    releaseYear = releaseYear
                )
            )
        }
    )
}