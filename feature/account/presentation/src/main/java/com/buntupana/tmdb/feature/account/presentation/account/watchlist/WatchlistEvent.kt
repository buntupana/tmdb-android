package com.buntupana.tmdb.feature.account.presentation.account.watchlist

import com.buntupana.tmdb.core.ui.filter_type.MediaFilter

sealed class WatchlistEvent {

    data object GetWatchLists : WatchlistEvent()

    data class GetWatchlist(val mediaFilter: MediaFilter) : WatchlistEvent()

    data object ChangeOrder : WatchlistEvent()
}