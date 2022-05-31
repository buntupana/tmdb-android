package com.buntupana.tmdb.feature.discover.presentation

import com.buntupana.tmdb.feature.discover.domain.entity.PopularType

sealed class DiscoverEvent {

    data class ChangePopularType(val popularType: PopularType) : DiscoverEvent()
}
