package com.buntupana.tmdb.feature.detail.presentation.person

import com.buntupana.tmdb.core.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data class PersonDetailDeepLinkRoute(
    val personId: String? = null
) : Route
