package com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites

import androidx.paging.PagingData
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.util.MediaItemRevealedViewEntity
import com.panabuntu.tmdb.core.common.entity.MediaType
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
    val movieItems: Flow<PagingData<MediaItemRevealedViewEntity>>? = null,
    val tvShowItems: Flow<PagingData<MediaItemRevealedViewEntity>>? = null,
    val order: Order = Order.DESC,
    val defaultPage: Int = MediaFilter.entries.indexOf(MediaFilter.MOVIES)
)