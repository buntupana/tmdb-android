package com.buntupana.tmdb.feature.detail.presentation.media

import com.panabuntu.tmdb.core.common.entity.MediaType

data class MediaDetailNavArgs(
    val mediaId: Long,
    val mediaType: MediaType,
    val backgroundColor: Int?
)
