package com.buntupana.tmdb.feature.detail.presentation.media

import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.presentation.navigation.Routes
import kotlinx.serialization.Serializable

@Serializable
data class MediaDetailNav(
    val mediaId: Long,
    val mediaType: MediaType,
    val backgroundColor: Int?
): Routes
