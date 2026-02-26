package com.buntupana.tmdb.feature.detail.presentation.rating

sealed class RatingSideEffect {
    data class AddRatingSuccess(val rating: Int?): RatingSideEffect()
}