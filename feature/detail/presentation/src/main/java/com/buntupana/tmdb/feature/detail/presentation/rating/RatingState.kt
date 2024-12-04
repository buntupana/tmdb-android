package com.buntupana.tmdb.feature.detail.presentation.rating

data class RatingState(
    val isLoading: Boolean = false,
    val mediaTitle: String,
    val rating: Int
)