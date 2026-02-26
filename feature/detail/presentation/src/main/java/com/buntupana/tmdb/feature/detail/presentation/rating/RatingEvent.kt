package com.buntupana.tmdb.feature.detail.presentation.rating

sealed class RatingEvent {

    data class SetRating(val rating: Int): RatingEvent()

    data object AddRating: RatingEvent()

    data object ClearRating: RatingEvent()
}