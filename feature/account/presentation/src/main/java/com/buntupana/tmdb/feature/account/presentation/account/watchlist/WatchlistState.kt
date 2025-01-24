package com.buntupana.tmdb.feature.account.presentation.account.watchlist

import androidx.paging.PagingData
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.model.Order
import kotlinx.coroutines.flow.Flow

data class WatchlistState(
    val isLoading: Boolean = false,
    val mediaType: MediaType = MediaType.MOVIE,
    val watchlistFilterSet: Set<MediaFilter> = setOf(
        MediaFilter.Movies,
        MediaFilter.TvShows,
    ),
    val watchlistFilterSelected: MediaFilter = MediaFilter.Movies,
    val isWatchlistLoadingError: Boolean = false,
    val movieItems: Flow<PagingData<MediaItem.Movie>>? = null,
    val tvShowItems: Flow<PagingData<MediaItem.TvShow>>? = null,
    val order: Order = Order.DESC,
    val selectedMediaType: MediaType = MediaType.MOVIE
)