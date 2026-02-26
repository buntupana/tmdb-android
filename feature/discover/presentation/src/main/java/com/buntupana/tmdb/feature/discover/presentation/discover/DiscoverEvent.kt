package com.buntupana.tmdb.feature.discover.presentation.discover

import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.PopularFilter
import com.buntupana.tmdb.feature.discover.presentation.filter_type.TrendingFilter

sealed class DiscoverEvent {

    data class ChangePopularType(val popularType: PopularFilter) : DiscoverEvent()
    data class ChangeFreeToWatchType(val freeToWatchFilter: MediaFilter): DiscoverEvent()
    data class ChangeTrendingType(val trendingFilter: TrendingFilter): DiscoverEvent()
}
