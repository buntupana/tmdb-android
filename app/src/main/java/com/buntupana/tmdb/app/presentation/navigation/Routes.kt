package com.buntupana.tmdb.app.presentation.navigation

import com.buntupana.tmdb.feature.detail.presentation.cast.CastDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.media.MediaDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.person.PersonDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.seasons.SeasonsDetailNavArgs
import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    data object Discover : Routes()

    @Serializable
    data object Search : Routes()

    @Serializable
    data class MediaDetail(
        val args: MediaDetailNavArgs
    ) : Routes()

    @Serializable
    data class CastDetail(
        val args: CastDetailNavArgs
    ) : Routes()

    @Serializable
    data class PersonDetail(
        val args: PersonDetailNavArgs
    ) : Routes()

    @Serializable
    data class SeasonDetail(
        val args: SeasonsDetailNavArgs
    ) : Routes()
}