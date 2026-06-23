package com.buntupana.tmdb.feature.seer.presentation.request

import com.buntupana.tmdb.core.ui.navigation.Route
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.serialization.Serializable

@Serializable
class SeerrRequestRoute(
    val mediaId: Long,
    val mediaType: MediaType
): Route