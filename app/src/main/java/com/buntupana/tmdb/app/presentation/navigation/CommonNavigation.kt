package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavHostController
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.presentation.util.encodeUrl
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.presentation.cast.CastDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailNavArgs

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

    override fun navigateToSeasons(mediaDetails: MediaDetails.TvShow, backgroundColor: Color?) {
//        destinationsNavigator.navigate(
//            SeasonsDetailScreenDestination(
//                tvShowDetails = mediaDetails, backgroundColor = backgroundColor?.toArgb()
//            )
//        )
    }

    override fun navigateToPerson(personId: Long) {
//        destinationsNavigator.navigate(PersonDetailScreenDestination(PersonDetailNavArgs(personId)))
    }
}