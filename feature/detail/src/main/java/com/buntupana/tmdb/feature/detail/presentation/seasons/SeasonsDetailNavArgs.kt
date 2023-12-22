package com.buntupana.tmdb.feature.detail.presentation.seasons

import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails

data class SeasonsDetailNavArgs(
    val tvShowDetails: MediaDetails.TvShow,
    val backgroundColor: Int?
)
