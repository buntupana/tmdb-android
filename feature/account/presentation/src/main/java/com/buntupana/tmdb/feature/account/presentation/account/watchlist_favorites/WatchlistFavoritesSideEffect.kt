package com.buntupana.tmdb.feature.account.presentation.account.watchlist_favorites

sealed class WatchlistFavoritesSideEffect {

    data object RefreshMediaItemList : WatchlistFavoritesSideEffect()
}