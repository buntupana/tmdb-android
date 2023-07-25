package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.ui.graphics.Color
import com.buntupana.tmdb.core.domain.entity.MediaType

interface DiscoverNavigator {
    fun navigateToMediaDetail(
        id: Long,
        mediaType: MediaType,
        backgroundColor: Color?
    )

    fun navigateToPerson(personId: Long)
    fun navigateToSearch()
}