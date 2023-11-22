package com.buntupana.tmdb.feature.detail.presentation.cast

import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails

data class CastDetailNavArgs(
    val mediaDetails: MediaDetails,
    val mediaType: MediaType,
    val backgroundColor: Int?
)
