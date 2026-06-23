package com.buntupana.tmdb.feature.seer.presentation.request

import com.buntupana.tmdb.feature.seer.domain.model.SeerMediaInfo
import com.panabuntu.tmdb.core.common.entity.MediaType

data class SeerrRequestState(
    val mediaId: Long,
    val mediaType: MediaType,
    val mediaInfo: SeerMediaInfo? = null,
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val isError: Boolean = false,
    val selectedSeasons: Set<Int> = emptySet(),
    val ableToSubmit: Boolean = false
)