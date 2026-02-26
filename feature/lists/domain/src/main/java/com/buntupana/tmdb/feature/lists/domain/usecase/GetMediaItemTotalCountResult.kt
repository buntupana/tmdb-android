package com.buntupana.tmdb.feature.lists.domain.usecase

data class GetMediaItemTotalCountResult(
    val movieTotalCount: Int = 0,
    val tvShowTotalCount: Int = 0
)