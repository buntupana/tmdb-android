package com.buntupana.tmdb.feature.search.domain.usecase

data class GetSearchResultCountResult(
    val moviesCount: Int,
    val tvShowsCount: Int,
    val personsCount: Int
)