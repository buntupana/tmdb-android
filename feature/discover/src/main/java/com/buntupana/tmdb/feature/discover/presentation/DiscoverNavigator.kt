package com.buntupana.tmdb.feature.discover.presentation

import com.buntupana.tmdb.core.domain.entity.MediaType

interface DiscoverNavigator {
    fun navigateToMediaDetail(id: Long, mediaType: MediaType)
    fun navigateToSearch()
}