package com.buntupana.tmdb.feature.account.presentation.account

import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.feature.lists.domain.model.MediaList
import com.panabuntu.tmdb.core.common.model.MediaItem

data class AccountState(
    val isUserLogged: Boolean = false,
    val username: String? = null,
    val avatarUrl: String? = null,
    val watchlistFilterSelected: MediaFilter = MediaFilter.MOVIES,
    val favoritesFilterSelected: MediaFilter = MediaFilter.MOVIES,
    val watchlistMediaItemList: List<MediaItem>? = null,
    val favoritesMediaItemList: List<MediaItem>? = null,
    val listsMediaItemList: List<MediaList>? = null,
    val isWatchlistLoadingError: Boolean = false,
    val isFavoritesLoadingError: Boolean = false,
    val isListsLoadingError: Boolean = false,
)