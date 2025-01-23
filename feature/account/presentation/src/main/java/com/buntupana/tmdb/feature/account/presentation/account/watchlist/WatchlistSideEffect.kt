package com.buntupana.tmdb.feature.account.presentation.account.watchlist

sealed class WatchlistSideEffect {

    data object GetWatchlist : WatchlistSideEffect()
}