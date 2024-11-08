package com.buntupana.tmdb.feature.detail.presentation

import androidx.compose.ui.graphics.Color
import com.buntupana.tmdb.core.domain.entity.MediaType

interface DetailNavigator {
    fun navigateBack()

    fun navigateToPerson(personId: Long)

    fun navigateToMediaDetail(
        mediaId: Long,
        mediaType: MediaType,
        backgroundColor: Color?
    )

    fun navigateToSearch()

    fun navigateToFullCast(
        mediaId: Long,
        mediaType: MediaType,
        mediaTitle: String,
        mediaReleaseYear: String?,
        mediaPosterUrl: String?,
        backgroundColor: Color?
    )

    fun navigateToSeasons(
        tvShowId: Long,
        tvShowTitle: String,
        releaseYear: String?,
        posterUrl: String?,
        backgroundColor: Color?
    )

    fun navigateToMainScreen()

    fun navigateToEpisodes(
        tvShowId: Long,
        seasonName: String,
        seasonNumber: Int,
        posterUrl: String?,
        backgroundColor: Color?,
        releaseYear: String?
    )
}