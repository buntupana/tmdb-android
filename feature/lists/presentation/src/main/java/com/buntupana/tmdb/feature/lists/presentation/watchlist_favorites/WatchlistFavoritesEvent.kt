package com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites

sealed class WatchlistFavoritesEvent {

    data object GetMediaItemList : WatchlistFavoritesEvent()

    data object ChangeOrder : WatchlistFavoritesEvent()
}