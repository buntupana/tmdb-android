package com.buntupana.tmdb.feature.account.presentation.watchlist_favorites

sealed class WatchlistFavoritesSideEffect {

    data object RefreshMediaItemList : WatchlistFavoritesSideEffect()
}