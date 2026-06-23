package com.buntupana.tmdb.feature.detail.presentation.media

import com.panabuntu.tmdb.core.common.entity.MediaType

sealed class MediaDetailSideEffect {
    data class NavigateToSeasonSelection(val mediaId: Long, val mediaType: MediaType): MediaDetailSideEffect()
}