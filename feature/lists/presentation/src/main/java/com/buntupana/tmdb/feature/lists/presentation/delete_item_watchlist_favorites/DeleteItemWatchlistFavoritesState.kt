package com.buntupana.tmdb.feature.lists.presentation.delete_item_watchlist_favorites

import com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.ScreenType

data class DeleteItemWatchlistFavoritesState(
    val isLoading: Boolean = false,
    val mediaName: String = "",
    val screenType: ScreenType = ScreenType.WATCHLIST
)