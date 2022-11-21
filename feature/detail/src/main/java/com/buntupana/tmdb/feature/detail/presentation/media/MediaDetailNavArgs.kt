package com.buntupana.tmdb.feature.detail.presentation.media

import com.buntupana.tmdb.core.domain.entity.MediaType

data class MediaDetailNavArgs(
    val mediaId: Long,
    val mediaType: MediaType
)
