package com.buntupana.tmdb.feature.discover.presentation

import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import com.buntupana.tmdb.feature.discover.domain.model.MovieItem

data class DiscoverState(
    val movieItemList: List<MovieItem> = emptyList(),
    val isLoading: Boolean = false,
    val popularType: PopularType = PopularType.STREAMING
)