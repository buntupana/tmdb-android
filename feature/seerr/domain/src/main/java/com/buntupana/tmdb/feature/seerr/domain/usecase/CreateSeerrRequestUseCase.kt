package com.buntupana.tmdb.feature.seerr.domain.usecase

import com.buntupana.tmdb.feature.seerr.domain.repository.SeerrRepository
import com.panabuntu.tmdb.core.common.entity.MediaType

class CreateSeerrRequestUseCase(
    private val repository: SeerrRepository
) {
    suspend operator fun invoke(
        mediaType: MediaType,
        tmdbId: Long,
        seasons: List<Int>? = null
    ) = repository.requestMedia(mediaType, tmdbId, seasons)
}
