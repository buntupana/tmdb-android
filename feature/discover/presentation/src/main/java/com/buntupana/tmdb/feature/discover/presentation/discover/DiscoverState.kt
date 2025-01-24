package com.buntupana.tmdb.feature.discover.presentation.discover

import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.PopularFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.TrendingFilter

data class DiscoverState(
    val isLoading: Boolean = false,
    val popularMediaItemList: List<com.panabuntu.tmdb.core.common.model.MediaItem> = emptyList(),
    val isPopularMediaLoadingError: Boolean = false,
    val freeToWatchMediaItemList: List<com.panabuntu.tmdb.core.common.model.MediaItem> = emptyList(),
    val isFreeToWatchMediaLoadingError: Boolean = false,
    val trendingMediaItemList: List<com.panabuntu.tmdb.core.common.model.MediaItem> = emptyList(),
    val isTrendingMediaLoadingError: Boolean = false,
    val trendingFilterSelected: TrendingFilter = TrendingFilter.TODAY,
    val popularFilterSelected: PopularFilter = PopularFilter.STREAMING,
    val freeToWatchFilterSelected: MediaFilter = MediaFilter.MOVIE,

    val popularFilterSet: Set<PopularFilter> = PopularFilter.entries.toSet(),
    val trendingFilterSet: Set<TrendingFilter> = TrendingFilter.entries.toSet(),
    val freeToWatchFilterSet: Set<MediaFilter> = MediaFilter.entries.toSet()
)