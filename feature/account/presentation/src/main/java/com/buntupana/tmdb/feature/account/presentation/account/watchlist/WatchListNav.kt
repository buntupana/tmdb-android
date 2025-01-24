package com.buntupana.tmdb.feature.account.presentation.account.watchlist

import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.navigation.Routes
import kotlinx.serialization.Serializable

@Serializable
data class WatchListNav(
    val mediaFilterSelected: MediaFilter
): Routes