package com.buntupana.tmdb.feature.detail.presentation.media

import com.buntupana.tmdb.core.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data class MediaDetailDeepLinkRoute(
    val movieId: String? = null,
    val tvShowId: String? = null
) : Route
