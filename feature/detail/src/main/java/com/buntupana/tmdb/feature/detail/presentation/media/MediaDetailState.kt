package com.buntupana.tmdb.feature.detail.presentation.media

import androidx.compose.ui.graphics.Color
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails

data class MediaDetailState(
    val mediaId: Long,
    val mediaType: MediaType,
    val isLoading: Boolean = false,
    val isGetContentError: Boolean = false,
    val mediaDetails: MediaDetails? = null,
    val backgroundColor: Color
)
