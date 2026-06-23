package com.buntupana.tmdb.feature.seer.domain.usecase

import com.buntupana.tmdb.feature.seer.domain.repository.SeerRepository
import com.panabuntu.tmdb.core.common.entity.MediaType

class CreateSeerRequestUseCase(
    private val repository: SeerRepository
) {
    suspend operator fun invoke(
        mediaType: MediaType,
        tmdbId: Long,
        seasons: List<Int>? = null
    ) = repository.requestMedia(mediaType, tmdbId, seasons)
}
