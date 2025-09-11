package com.buntupana.tmdb.feature.detail.presentation.seasons

import com.buntupana.tmdb.feature.detail.domain.model.Season

data class SeasonsDetailState(
    val isLoading: Boolean = false,
    val isGetSeasonsError: Boolean = false,
    val tvShowId: Long,
    val tvShowName: String = "",
    val posterUrl: String? = null,
    val releaseYear: String? = null,
    val backgroundColor: Int?,
    val seasonList: List<Season>? = null
)
