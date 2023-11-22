package com.buntupana.tmdb.feature.detail.presentation

import androidx.compose.ui.graphics.Color
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails

interface DetailNavigator {
    fun navigateBack()

    fun navigateToPerson(personId: Long)

    fun navigateToMediaDetail(
        id: Long,
        mediaType: MediaType,
        backgroundColor: Color?
    )

    fun navigateToSearch()

    fun navigateToFullCast(
        mediaDetails: MediaDetails,
        mediaType: MediaType,
        backgroundColor: Color?
    )

    fun navigateToMainScreen()
}