package com.buntupana.tmdb.feature.lists.presentation.delete_item_watchlist_favorites

sealed class DeleteItemWatchlistFavoritesSideEffect {

    data object DeleteSuccess : DeleteItemWatchlistFavoritesSideEffect()
}