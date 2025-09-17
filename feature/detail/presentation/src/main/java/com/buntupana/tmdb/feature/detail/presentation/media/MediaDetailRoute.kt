package com.buntupana.tmdb.feature.detail.presentation.media

import com.buntupana.tmdb.core.ui.navigation.Route
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.serialization.Serializable

@Serializable
data class MediaDetailRoute(
    val mediaId: Long,
    val mediaType: MediaType,
    val backgroundColor: Int?
): Route
