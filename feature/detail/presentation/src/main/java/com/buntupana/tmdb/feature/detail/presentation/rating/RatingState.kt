package com.buntupana.tmdb.feature.detail.presentation.rating

import com.buntupana.tmdb.core.ui.util.UiText

data class RatingState(
    val isLoading: Boolean = false,
    val mediaTitle: String,
    val rating: Int,
    val ratingTitle: UiText = UiText.DynamicString("")
)