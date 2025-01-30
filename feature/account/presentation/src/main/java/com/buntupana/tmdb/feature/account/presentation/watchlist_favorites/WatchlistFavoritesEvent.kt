package com.buntupana.tmdb.feature.account.presentation.watchlist_favorites

sealed class WatchlistFavoritesEvent {

    data object GetMediaItemList : WatchlistFavoritesEvent()

    data object ChangeOrder : WatchlistFavoritesEvent()
}