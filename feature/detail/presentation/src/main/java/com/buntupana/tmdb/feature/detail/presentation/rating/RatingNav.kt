package com.buntupana.tmdb.feature.detail.presentation.rating

import com.buntupana.tmdb.core.ui.navigation.Route
import com.buntupana.tmdb.core.ui.util.navType
import kotlinx.serialization.Serializable

@Serializable
data class RatingNav(
    val ratingMediaType: RatingMediaType
): Route {
    companion object  {
        val typeMap = mapOf(navType<RatingMediaType>())
    }
}