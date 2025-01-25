package com.buntupana.tmdb.feature.account.domain.usecase

data class GetMediaItemTotalCountResult(
    val movieTotalCount: Int = 0,
    val tvShowTotalCount: Int = 0
)