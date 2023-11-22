package com.buntupana.tmdb.app.presentation.navigation

import com.buntupana.tmdb.feature.detail.presentation.destinations.CastDetailScreenDestination
import com.buntupana.tmdb.feature.detail.presentation.destinations.MediaDetailScreenDestination
import com.buntupana.tmdb.feature.detail.presentation.destinations.PersonDetailScreenDestination
import com.buntupana.tmdb.feature.discover.presentation.destinations.DiscoverScreenDestination
import com.buntupana.tmdb.feature.search.presentation.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

    object NavGraphs {

        val root = object: NavGraphSpec {
            override val route = "root"

            override val startRoute = DiscoverScreenDestination

            // Here we have to add all destinations
            override val destinationsByRoute = listOf<DestinationSpec<*>>(
                DiscoverScreenDestination,
                MediaDetailScreenDestination,
                SearchScreenDestination,
                PersonDetailScreenDestination,
                CastDetailScreenDestination
            ).associateBy { it.route }
        }
    }