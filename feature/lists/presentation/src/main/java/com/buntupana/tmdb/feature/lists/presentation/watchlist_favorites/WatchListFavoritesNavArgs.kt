package com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites

import com.buntupana.tmdb.core.ui.filter_type.MediaFilter

data class WatchListFavoritesNavArgs(
    val screenType: ScreenType,
    val mediaFilterSelected: MediaFilter
)