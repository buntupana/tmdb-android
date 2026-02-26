package com.buntupana.tmdb.feature.detail.presentation.media

import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.panabuntu.tmdb.core.common.entity.MediaType

data class MediaDetailState(
    val mediaId: Long,
    val mediaType: MediaType,
    val isLoading: Boolean = true,
    val isGetContentError: Boolean = false,
    val mediaDetails: MediaDetails? = null,
    val backgroundColor: Int?,
    val isUserLoggedIn: Boolean = false,
    val isFavoriteLoading: Boolean = false,
    val isWatchlistLoading: Boolean = false,
    val isRatingLoading: Boolean = false,
    val isRateable: Boolean = false,
    val imageList: List<String>? = null,
    val showImageViewer: Boolean = false,
)
