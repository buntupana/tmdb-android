package com.buntupana.tmdb.feature.account.domain.model

import com.panabuntu.tmdb.core.common.entity.MediaType

data class MediaItemBasic(
    val id: Long,
    val mediaType: MediaType
)
