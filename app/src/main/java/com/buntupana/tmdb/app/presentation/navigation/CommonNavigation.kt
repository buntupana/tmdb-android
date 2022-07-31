package com.buntupana.tmdb.app.presentation.navigation

import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
import com.buntupana.tmdb.feature.detail.presentation.destinations.MediaDetailScreenDestination
import com.buntupana.tmdb.feature.discover.presentation.DiscoverNavigator
import com.buntupana.tmdb.feature.search.presentation.SearchNavigator
import com.buntupana.tmdb.feature.search.presentation.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class CommonNavigation(
    private val destinationsNavigator: DestinationsNavigator
) : DiscoverNavigator, DetailNavigator, SearchNavigator {

    override fun navigateToMediaDetail(id: Long, mediaType: MediaType) {
        destinationsNavigator.navigate(MediaDetailScreenDestination(id, mediaType))
    }

    override fun navigateToSearch() {
        destinationsNavigator.navigate(SearchScreenDestination())
    }
}