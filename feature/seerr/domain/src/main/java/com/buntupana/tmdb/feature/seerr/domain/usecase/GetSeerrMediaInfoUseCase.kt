package com.buntupana.tmdb.feature.seerr.domain.usecase

import com.buntupana.tmdb.feature.seerr.domain.repository.SeerrRepository
import com.panabuntu.tmdb.core.common.entity.MediaType

class GetSeerrMediaInfoUseCase(
    private val repository: SeerrRepository
) {
    suspend operator fun invoke(
        mediaType: MediaType,
        mediaId: Long
    ) = repository.getMediaInfo(mediaType, mediaId)
}
