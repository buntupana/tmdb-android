package com.buntupana.tmdb.app.presentation.nav3

import androidx.navigation3.runtime.NavKey
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.serialization.Serializable

@Serializable
sealed interface RouteNav3: NavKey {

    @Serializable
    data object Home: RouteNav3

    @Serializable
    data object Discover: RouteNav3

    @Serializable
    data object Account: RouteNav3

    @Serializable
    sealed class MediaList(
        val mediaType: MediaType
    ) : RouteNav3 {
        @Serializable
        data class Movie(
            private val _mediaType: MediaType = MediaType.MOVIE
        ) : MediaList(_mediaType)

        @Serializable
        data class TvShow(
            private val _mediaType: MediaType = MediaType.TV_SHOW
        ) : MediaList(_mediaType)

        @Serializable
        data object MediaDetails: RouteNav3
    }

    @Serializable
    data object Search: RouteNav3

    @Serializable
    data class MediaDetail(
        val mediaId: Long,
        val mediaType: MediaType,
        val backgroundColor: Int?
    ): RouteNav3


}