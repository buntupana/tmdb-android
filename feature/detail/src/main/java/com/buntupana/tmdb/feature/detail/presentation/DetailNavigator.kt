package com.buntupana.tmdb.feature.detail.presentation

import com.buntupana.tmdb.core.domain.entity.MediaType

interface DetailNavigator {
    fun navigateToPerson(personId: Long)
    fun navigateToMediaDetail(id: Long, mediaType: MediaType)
}