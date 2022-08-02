package com.buntupana.tmdb.feature.detail.domain.model

data class Credits(
    val castList: List<CastPersonItem>,
    val crewList: List<CrewPersonItem>
)
