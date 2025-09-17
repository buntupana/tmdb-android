package com.buntupana.tmdb.feature.detail.presentation.rating

import com.buntupana.tmdb.core.ui.navigation.Route
import com.buntupana.tmdb.core.ui.util.navType
import kotlinx.serialization.Serializable

@Serializable
data class RatingRoute(
    val ratingMediaType: RatingMediaType
): Route {
    companion object Companion {
        val typeMap = mapOf(navType<RatingMediaType>())
    }
}