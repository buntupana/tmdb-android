package com.buntupana.tmdb.feature.discover.presentation.media_list

import com.buntupana.tmdb.core.ui.navigation.Route
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.serialization.Serializable

@Serializable
sealed class MediaListNav(
    val mediaType: MediaType
) : Route {
    @Serializable
    data class Movie(
        private val _mediaType: MediaType = MediaType.MOVIE
    ) : MediaListNav(_mediaType)

    @Serializable
    data class TvShow(
        private val _mediaType: MediaType = MediaType.TV_SHOW
    ) : MediaListNav(_mediaType)
}