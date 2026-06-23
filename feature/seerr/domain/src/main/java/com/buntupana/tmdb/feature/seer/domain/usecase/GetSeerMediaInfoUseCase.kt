package com.buntupana.tmdb.feature.seer.domain.usecase

import com.buntupana.tmdb.feature.seer.domain.repository.SeerRepository
import com.panabuntu.tmdb.core.common.entity.MediaType

class GetSeerMediaInfoUseCase(
    private val repository: SeerRepository
) {
    suspend operator fun invoke(
        mediaType: MediaType,
        mediaId: Long
    ) = repository.getMediaInfo(mediaType, mediaId)
}
