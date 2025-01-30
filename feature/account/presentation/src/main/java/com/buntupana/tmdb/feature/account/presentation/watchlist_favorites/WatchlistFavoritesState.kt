package com.buntupana.tmdb.feature.account.presentation.watchlist_favorites

import androidx.paging.PagingData
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.model.Order
import kotlinx.coroutines.flow.Flow

data class WatchlistFavoritesState(
    val screenType: ScreenType,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val mediaType: MediaType = MediaType.MOVIE,
    val watchlistFilterSelected: MediaFilter = MediaFilter.MOVIES,
    val isWatchlistLoadingError: Boolean = false,
    val movieItemsTotalCount: Int? = null,
    val tvShowItemsTotalCount: Int? = null,
    val movieItems: Flow<PagingData<MediaItem.Movie>>? = null,
    val tvShowItems: Flow<PagingData<MediaItem.TvShow>>? = null,
    val order: Order = Order.DESC,
    val defaultPage: Int = MediaFilter.entries.indexOf(MediaFilter.MOVIES)
)