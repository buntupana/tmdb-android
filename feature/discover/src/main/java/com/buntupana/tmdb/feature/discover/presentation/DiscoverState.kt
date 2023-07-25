package com.buntupana.tmdb.feature.discover.presentation

import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.feature.discover.presentation.filter_type.FreeToWatchFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.PopularFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.TrendingFilter

data class DiscoverState(
    val isLoading: Boolean = false,
    val popularMediaItemList: List<MediaItem> = emptyList(),
    val freeToWatchMediaItemList: List<MediaItem> = emptyList(),
    val trendingMediaItemList: List<MediaItem> = emptyList(),
    val trendingFilterSelected: TrendingFilter = TrendingFilter.Today,
    val popularFilterSelected: PopularFilter = PopularFilter.Streaming,
    val freeToWatchFilterSelected: FreeToWatchFilter = FreeToWatchFilter.Movies,

    val popularFilterSet: Set<PopularFilter> = setOf(
        PopularFilter.Streaming,
        PopularFilter.OnTv,
        PopularFilter.ForRent,
        PopularFilter.InTheatres,
    ),
    val trendingFilterSet: Set<TrendingFilter> = setOf(
        TrendingFilter.Today,
        TrendingFilter.ThisWeek
    ),
    val freeToWatchFilterSet: Set<FreeToWatchFilter> = setOf(
        FreeToWatchFilter.Movies,
        FreeToWatchFilter.TvShows
    )
)