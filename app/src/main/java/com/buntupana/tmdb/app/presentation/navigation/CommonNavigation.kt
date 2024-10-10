package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavHostController
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.presentation.util.encodeUrl
import com.buntupana.tmdb.feature.detail.presentation.cast.CastDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.episodes.EpisodesDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.person.PersonDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.seasons.SeasonsDetailNavArgs

class CommonNavigation(
    private val navHostController: NavHostController
) : CommonNavigationBase {

    override fun navigateBack() {
        navHostController.navigateUp()
    }

    override fun navigateToMainScreen() {
        navHostController.popBackStack(
            route = Routes.Discover,
            inclusive = false
        )
    }

    override fun navigateToSearch() {
        navHostController.navigate(Routes.Search)
    }

    override fun navigateToMediaDetail(
        mediaId: Long,
        mediaType: MediaType,
        backgroundColor: Color?
    ) {
        navHostController.navigate(
            Routes.MediaDetail(
                args = MediaDetailNavArgs(
                    mediaId = mediaId,
                    mediaType = mediaType,
                    backgroundColor = backgroundColor?.toArgb()
                )
            )
        )
    }

    override fun navigateToFullCast(
        mediaId: Long,
        mediaType: MediaType,
        mediaTitle: String,
        mediaReleaseYear: String?,
        mediaPosterUrl: String?,
        backgroundColor: Color?
    ) {
        navHostController.navigate(
            Routes.CastDetail(
                args = CastDetailNavArgs(
                    mediaId = mediaId,
                    mediaType = mediaType,
                    mediaTitle = mediaTitle,
                    backgroundColor = backgroundColor?.toArgb(),
                    releaseYear = mediaReleaseYear,
                    posterUrlEncoded = mediaPosterUrl?.encodeUrl()
                )
            )
        )
    }

    override fun navigateToPerson(personId: Long) {
        navHostController.navigate(
            Routes.PersonDetail(
                args = PersonDetailNavArgs(personId)
            )
        )
    }

    override fun navigateToSeasons(
        tvShowId: Long,
        tvShowTitle: String,
        releaseYear: String?,
        posterUrl: String?,
        backgroundColor: Color?
    ) {
        navHostController.navigate(
            Routes.SeasonDetail(
                args = SeasonsDetailNavArgs(
                    tvShowId = tvShowId,
                    tvShowName = tvShowTitle,
                    posterUrlEncoded = posterUrl?.encodeUrl(),
                    releaseYear = releaseYear,
                    backgroundColor = backgroundColor?.toArgb()
                )
            )
        )
    }

    override fun navigateToEpisodes(
        tvShowId: Long,
        seasonName: String,
        seasonNumber: Int,
        posterUrl: String?,
        backgroundColor: Color?,
        releaseYear: String?
    ) {
        navHostController.navigate(
            Routes.EpisodesDetail(
                args = EpisodesDetailNavArgs(
                    tvShowId = tvShowId,
                    seasonNumber = seasonNumber,
                    seasonName = seasonName,
                    posterUrlEncoded = posterUrl?.encodeUrl(),
                    releaseYear = releaseYear,
                    backgroundColor = backgroundColor?.toArgb()
                )
            )
        )
    }
}