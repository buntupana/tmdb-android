package com.buntupana.tmdb.feature.detail.domain.usecase

data class GetSeasonDetailsUseCaseParams(
    val tvShowId: Long,
    val seasonNumber: Int
)