package com.buntupana.tmdb.feature.detail.presentation.media

import androidx.compose.ui.graphics.Color
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.panabuntu.tmdb.core.common.entity.MediaType

data class MediaDetailState(
    val mediaId: Long,
    val mediaType: MediaType,
    val isLoading: Boolean = false,
    val isGetContentError: Boolean = false,
    val mediaDetails: MediaDetails? = null,
    val backgroundColor: Color
)
