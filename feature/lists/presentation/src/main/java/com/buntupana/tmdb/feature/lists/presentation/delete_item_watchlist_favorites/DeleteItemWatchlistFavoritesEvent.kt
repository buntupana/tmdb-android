package com.buntupana.tmdb.feature.lists.presentation.delete_item_watchlist_favorites

sealed class DeleteItemWatchlistFavoritesEvent {

    data class Init(val deleteItemWatchlistFavoritesNav: DeleteItemWatchlistFavoritesNav) : DeleteItemWatchlistFavoritesEvent()

    data object ConfirmDelete : DeleteItemWatchlistFavoritesEvent()
}