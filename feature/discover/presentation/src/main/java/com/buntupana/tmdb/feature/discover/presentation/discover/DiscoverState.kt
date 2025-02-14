package com.buntupana.tmdb.feature.discover.presentation.discover

import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.PopularFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.TrendingFilter
import com.panabuntu.tmdb.core.common.model.MediaItem

data class DiscoverState(
    val isLoading: Boolean = false,
    val popularMediaItemList: List<MediaItem>? = null,
    val isPopularMediaLoadingError: Boolean = false,
    val freeToWatchMediaItemList: List<MediaItem>? = null,
    val isFreeToWatchMediaLoadingError: Boolean = false,
    val trendingMediaItemList: List<MediaItem>? = null,
    val isTrendingMediaLoadingError: Boolean = false,
    val trendingFilterSelected: TrendingFilter = TrendingFilter.TODAY,
    val popularFilterSelected: PopularFilter = PopularFilter.STREAMING,
    val freeToWatchFilterSelected: MediaFilter = MediaFilter.MOVIES,

    val popularFilterSet: Set<PopularFilter> = PopularFilter.entries.toSet(),
    val trendingFilterSet: Set<TrendingFilter> = TrendingFilter.entries.toSet(),
    val freeToWatchFilterSet: Set<MediaFilter> = MediaFilter.entries.toSet()
)