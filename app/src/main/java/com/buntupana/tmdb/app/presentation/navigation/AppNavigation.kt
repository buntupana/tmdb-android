package com.buntupana.tmdb.app.presentation.navigation

import com.buntupana.tmdb.feature.detail.presentation.destinations.MediaDetailScreenDestination
import com.buntupana.tmdb.feature.discover.presentation.destinations.DiscoverScreenDestination
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

    object NavGraphs {

        val root = object: NavGraphSpec {
            override val route = "root"

            override val startRoute = DiscoverScreenDestination

            override val destinationsByRoute = listOf<DestinationSpec<*>>(
                DiscoverScreenDestination,
                MediaDetailScreenDestination
            ).associateBy { it.route }
        }
    }