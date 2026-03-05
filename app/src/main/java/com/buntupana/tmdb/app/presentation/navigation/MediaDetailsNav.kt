package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.buntupana.tmdb.app.presentation.getViewModel
import com.buntupana.tmdb.app.presentation.nav3.Navigator
import com.buntupana.tmdb.app.presentation.nav3.RouteNav3
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailScreen
import com.buntupana.tmdb.feature.detail.presentation.rating.RatingMediaType
import com.panabuntu.tmdb.core.common.entity.MediaType

@Composable
fun MediaDetailsNav(
    navigator: Navigator,
    route: RouteNav3.MediaDetail
) {

    val navArgs = MediaDetailNavArgs(route.mediaId, route.mediaType, route.backgroundColor)

    MediaDetailScreen(
        viewModel = getViewModel(navArgs),
        onBackClick = navigator::goBack,
        onSearchClick = { navigator.navigate(RouteNav3.Search) },
        onPersonClick = { personId ->
//            navRoutesMain.navigate(
//                PersonDetailRoute(personId = personId)
//            )
        },
        onFullCastClick = { mediaId, mediaType, mediaTitle, mediaReleaseYear, mediaPosterUrl, backgroundColor ->
//            navRoutesMain.navigate(
//                CastDetailRoute(
//                    mediaId = mediaId,
//                    mediaType = mediaType,
//                    mediaTitle = mediaTitle,
//                    releaseYear = mediaReleaseYear,
//                    posterUrl = mediaPosterUrl,
//                    backgroundColor = backgroundColor.toArgb()
//                )
//            )
        },
        onSeasonClick = { tvShowId, seasonName, seasonNumber, posterUrl, backgroundColor, releaseYear ->
//            navRoutesMain.navigate(
//                EpisodesDetailRoute(
//                    tvShowId = tvShowId,
//                    seasonName = seasonName,
//                    seasonNumber = seasonNumber,
//                    posterUrl = posterUrl,
//                    backgroundColor = backgroundColor.toArgb(),
//                    releaseYear = releaseYear
//                )
//            )
        },
        onAllSeasonsClick = { tvShowId, tvShowTitle, releaseYear, posterUrl, backgroundColor ->
//            navRoutesMain.navigate(
//                SeasonsDetailRoute(
//                    tvShowId = tvShowId,
//                    tvShowName = tvShowTitle,
//                    releaseYear = releaseYear,
//                    posterUrl = posterUrl,
//                    backgroundColor = backgroundColor.toArgb()
//                )
//            )
        },
        onRecommendationClick = { mediaId, mediaType, backgroundColor ->
            navigator.navigate(
                RouteNav3.MediaDetail(
                    mediaId = mediaId,
                    mediaType = mediaType,
                    backgroundColor = backgroundColor?.toArgb()
                )
            )
        },
        onLogoClick = {
//            navRoutesMain.popBackStack(route = HomeRoute::class, inclusive = false)
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
//                navRoutesMain.navigate(
//                    RatingRoute(it)
//                )
            }

        },
        onManageListClick = { mediaId, mediaType, mediaName, mediaPosterUrl, backgroundColor, releaseYear ->
//            navRoutesMain.navigate(
//                ManageListsRoute(
//                    mediaId = mediaId,
//                    mediaType = mediaType,
//                    posterUrl = mediaPosterUrl,
//                    mediaName = mediaName,
//                    backgroundColor = backgroundColor,
//                    releaseYear = releaseYear
//                )
//            )
        }
    )
}