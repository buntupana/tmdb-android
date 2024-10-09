package com.buntupana.tmdb.feature.search.presentation

import androidx.compose.ui.graphics.Color
import com.buntupana.tmdb.core.domain.entity.MediaType

interface SearchNavigator {
    fun navigateToMediaDetail(
        mediaId: Long,
        mediaType: MediaType,
        backgroundColor: Color?
    )

    fun navigateToPerson(personId: Long)
}