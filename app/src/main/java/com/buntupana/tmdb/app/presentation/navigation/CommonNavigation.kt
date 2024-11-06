package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.feature.detail.presentation.cast.CastDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.episodes.EpisodesDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.person.PersonDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.seasons.SeasonsDetailNavArgs

class CommonNavigation(
    private val navRoutesMain: NavRoutesMain
) : CommonNavigationBase {

    override fun navigateBack() {
        navRoutesMain.popBackStack()
    }

    override fun navigateToMainScreen() {
        navRoutesMain.popBackStack(
            destination = Routes.Discover::class,
            inclusive = true
        )
    }

    override fun navigateToSearch() {
        navRoutesMain.navigate(Routes.Search)
    }

    override fun navigateToMediaDetail(
        mediaId: Long,
        mediaType: MediaType,
        backgroundColor: Color?
    ) {
        navRoutesMain.navigate(
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
        navRoutesMain.navigate(
            Routes.CastDetail(
                args = CastDetailNavArgs(
                    mediaId = mediaId,
                    mediaType = mediaType,
                    mediaTitle = mediaTitle,
                    backgroundColor = backgroundColor?.toArgb(),
                    releaseYear = mediaReleaseYear,
                    posterUrl = mediaPosterUrl
                )
            )
        )
    }

    override fun navigateToPerson(personId: Long) {
        navRoutesMain.navigate(
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
        navRoutesMain.navigate(
            Routes.SeasonDetail(
                args = SeasonsDetailNavArgs(
                    tvShowId = tvShowId,
                    tvShowName = tvShowTitle,
                    posterUrl = posterUrl,
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
        navRoutesMain.navigate(
            Routes.EpisodesDetail(
                args = EpisodesDetailNavArgs(
                    tvShowId = tvShowId,
                    seasonNumber = seasonNumber,
                    seasonName = seasonName,
                    posterUrl = posterUrl,
                    releaseYear = releaseYear,
                    backgroundColor = backgroundColor?.toArgb()
                )
            )
        )
    }
}