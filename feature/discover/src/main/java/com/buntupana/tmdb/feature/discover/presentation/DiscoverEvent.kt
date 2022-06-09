package com.buntupana.tmdb.feature.discover.presentation

import com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType

sealed class DiscoverEvent {

    data class ChangePopularType(val popularType: PopularType) : DiscoverEvent()
    data class ChangeFreeToWatchType(val freeToWatchType: FreeToWatchType): DiscoverEvent()
    data class ChangeTrendingType(val trendingType: TrendingType): DiscoverEvent()
}
