package com.buntupana.tmdb.feature.account.presentation.account.watchlist

import com.buntupana.tmdb.core.ui.navigation.Routes
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.serialization.Serializable

@Serializable
data class WatchListNav(
    val mediaTypeSelected: MediaType
): Routes