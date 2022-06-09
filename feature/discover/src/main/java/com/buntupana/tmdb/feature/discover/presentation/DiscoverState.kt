package com.buntupana.tmdb.feature.discover.presentation

import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType

data class DiscoverState(
    val popularMediaItemList: List<MediaItem> = emptyList(),
    val freeToWatchMediaItemList: List<MediaItem> = emptyList(),
    val trendingMediaItemList: List<MediaItem> = emptyList(),
    val isLoading: Boolean = false,
    val popularType: PopularType = PopularType.STREAMING,
    val freeToWatchType: FreeToWatchType = FreeToWatchType.MOVIES,
    val trendingType: TrendingType = TrendingType.TODAY
)