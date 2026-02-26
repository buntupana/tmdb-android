package com.buntupana.tmdb.feature.lists.domain.model

import com.panabuntu.tmdb.core.common.entity.MediaType

data class MediaItemBasic(
    val id: Long,
    val mediaType: MediaType
)
