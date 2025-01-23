package com.buntupana.tmdb.feature.account.presentation.account.account

import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.panabuntu.tmdb.core.common.model.MediaItem

data class AccountState(
    val isUserLogged: Boolean = false,
    val username: String? = null,
    val avatarUrl: String? = null,
    val watchlistFilterSet: Set<MediaFilter> = setOf(
        MediaFilter.Movies,
        MediaFilter.TvShows,
    ),
    val watchlistFilterSelected: MediaFilter = MediaFilter.Movies,
    val watchlistMediaItemList: List<MediaItem> = emptyList(),
    val isWatchlistLoadingError: Boolean = false
)