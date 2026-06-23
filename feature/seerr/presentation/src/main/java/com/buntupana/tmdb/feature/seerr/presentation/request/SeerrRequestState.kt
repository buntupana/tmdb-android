package com.buntupana.tmdb.feature.seerr.presentation.request

import com.buntupana.tmdb.feature.seerr.domain.model.SeerrMediaInfo
import com.panabuntu.tmdb.core.common.entity.MediaType

data class SeerrRequestState(
    val mediaId: Long,
    val mediaType: MediaType,
    val mediaInfo: SeerrMediaInfo? = null,
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val isError: Boolean = false,
    val selectedSeasons: Set<Int> = emptySet(),
    val ableToSubmit: Boolean = false
)