package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.feature.detail.presentation.cast.CastDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.episodes.EpisodesDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.person.PersonDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.seasons.SeasonsDetailNavArgs
import timber.log.Timber

class CommonNavigation(
    private val navRoutesMain: NavRoutesMain
) : CommonNavigationBase {

    override fun navigateBack() {
        Timber.d("navigateBack() called")
        navRoutesMain.popBackStack()
    }

    override fun navigateToMainScreen() {
        Timber.d("navigateToMainScreen() called")
        navRoutesMain.popBackStack(destination = Routes.Home::class)
    }

    override fun navigateToSearch() {
        Timber.d("navigateToSearch() called")
        navRoutesMain.navigate(Routes.Search)
    }

    override fun navigateToMediaDetail(
        mediaId: Long,
        mediaType: MediaType,
        backgroundColor: Color?
    ) {
        Timber.d("navigateToMediaDetail() called with: mediaId = [$mediaId], mediaType = [$mediaType], backgroundColor = [$backgroundColor]")
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
        Timber.d("navigateToFullCast() called with: mediaId = [$mediaId], mediaType = [$mediaType], mediaTitle = [$mediaTitle], mediaReleaseYear = [$mediaReleaseYear], mediaPosterUrl = [$mediaPosterUrl], backgroundColor = [$backgroundColor]")
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
        Timber.d("navigateToPerson() called with: personId = [$personId]")
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
        Timber.d("navigateToSeasons() called with: tvShowId = [$tvShowId], tvShowTitle = [$tvShowTitle], releaseYear = [$releaseYear], posterUrl = [$posterUrl], backgroundColor = [$backgroundColor]")
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
        Timber.d("navigateToEpisodes() called with: tvShowId = [$tvShowId], seasonName = [$seasonName], seasonNumber = [$seasonNumber], posterUrl = [$posterUrl], backgroundColor = [$backgroundColor], releaseYear = [$releaseYear]")
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