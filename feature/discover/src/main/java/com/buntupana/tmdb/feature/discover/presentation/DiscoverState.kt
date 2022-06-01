package com.buntupana.tmdb.feature.discover.presentation

import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType

data class DiscoverState(
    val popularMediaItemList: List<MediaItem> = emptyList(),
    val freeToWatchMediaItemList: List<MediaItem> = emptyList(),
    val isLoading: Boolean = false,
    val popularType: PopularType = PopularType.STREAMING,
)