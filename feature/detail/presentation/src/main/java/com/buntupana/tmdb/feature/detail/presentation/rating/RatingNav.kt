package com.buntupana.tmdb.feature.detail.presentation.rating

import com.buntupana.tmdb.core.ui.navigation.Routes
import com.buntupana.tmdb.core.ui.util.navType
import kotlinx.serialization.Serializable

@Serializable
data class RatingNav(
    val ratingMediaType: RatingMediaType
): Routes {
    companion object  {
        val typeMap = mapOf(navType<RatingMediaType>())
    }
}