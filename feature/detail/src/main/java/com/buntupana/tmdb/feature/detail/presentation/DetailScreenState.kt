package com.buntupana.tmdb.feature.detail.presentation

import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails

data class DetailScreenState(
    val isLoading: Boolean = false,
    val mediaDetails: MediaDetails? = null
)
