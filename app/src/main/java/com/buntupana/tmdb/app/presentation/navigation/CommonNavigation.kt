package com.buntupana.tmdb.app.presentation.navigation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.feature.detail.presentation.DetailNavigator
import com.buntupana.tmdb.feature.detail.presentation.PersonDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.destinations.MediaDetailScreenDestination
import com.buntupana.tmdb.feature.detail.presentation.destinations.PersonDetailScreenDestination
import com.buntupana.tmdb.feature.discover.presentation.DiscoverNavigator
import com.buntupana.tmdb.feature.search.presentation.SearchNavigator
import com.buntupana.tmdb.feature.search.presentation.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class CommonNavigation(
    private val destinationsNavigator: DestinationsNavigator
) : DiscoverNavigator, DetailNavigator, SearchNavigator {

    override fun navigateToMediaDetail(id: Long, mediaType: MediaType, backgroundColor: Color?) {
        destinationsNavigator.navigate(
            MediaDetailScreenDestination(id, mediaType, backgroundColor?.toArgb())
        )
    }

    override fun navigateBack() {
        destinationsNavigator.navigateUp()
    }

    override fun navigateToSearch() {
        destinationsNavigator.navigate(SearchScreenDestination())
    }

    override fun navigateToPerson(personId: Long) {
        destinationsNavigator.navigate(PersonDetailScreenDestination(PersonDetailNavArgs(personId)))
    }
}