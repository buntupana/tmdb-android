package com.buntupana.tmdb.feature.detail.presentation

import com.buntupana.tmdb.feature.detail.domain.model.MovieDetails

data class DetailScreenState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null
)
