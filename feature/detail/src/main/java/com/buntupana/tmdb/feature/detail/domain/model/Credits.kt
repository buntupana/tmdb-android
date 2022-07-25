package com.buntupana.tmdb.feature.detail.domain.model

data class Credits(
    val castList: List<CastItem>,
    val crewList: List<CrewItem>
)
