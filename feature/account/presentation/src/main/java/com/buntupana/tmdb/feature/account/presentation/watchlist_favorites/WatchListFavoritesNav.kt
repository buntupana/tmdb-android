package com.buntupana.tmdb.feature.account.presentation.watchlist_favorites

import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data class WatchListFavoritesNav(
    val screenType: ScreenType,
    val mediaFilterSelected: MediaFilter
): Route