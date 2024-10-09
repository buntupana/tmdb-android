package com.buntupana.tmdb.feature.detail.presentation

import androidx.compose.ui.graphics.Color
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails

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
        mediaDetails: MediaDetails.TvShow,
        backgroundColor: Color?
    )

    fun navigateToMainScreen()
}