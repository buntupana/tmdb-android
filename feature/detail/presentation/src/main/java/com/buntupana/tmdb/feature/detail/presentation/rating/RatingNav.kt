package com.buntupana.tmdb.feature.detail.presentation.rating

import com.buntupana.tmdb.core.ui.navigation.Routes
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.serialization.Serializable

@Serializable
data class RatingNav(
    val mediaType: MediaType,
    val mediaId: Long,
    val mediaTitle: String,
    val rating: Int?
): Routes