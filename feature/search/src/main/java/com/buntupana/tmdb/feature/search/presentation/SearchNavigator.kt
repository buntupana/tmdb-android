package com.buntupana.tmdb.feature.search.presentation

import com.buntupana.tmdb.core.domain.entity.MediaType

interface SearchNavigator {
    fun navigateToMediaDetail(id: Long, mediaType: MediaType)
}