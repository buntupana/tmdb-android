package com.buntupana.tmdb.feature.account.presentation.account.account

import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.panabuntu.tmdb.core.common.model.MediaItem

data class AccountState(
    val isUserLogged: Boolean = false,
    val username: String? = null,
    val avatarUrl: String? = null,
    val watchlistFilterSelected: MediaFilter = MediaFilter.MOVIES,
    val favoritesFilterSelected: MediaFilter = MediaFilter.MOVIES,
    val watchlistMediaItemList: List<MediaItem> = emptyList(),
    val favoritesMediaItemList: List<MediaItem> = emptyList(),
    val isWatchlistLoadingError: Boolean = false,
    val isFavoritesLoadingError: Boolean = false
)