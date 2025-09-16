package com.buntupana.tmdb.feature.lists.domain.usecase

import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result

class SetMediaFavoriteUseCase(
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(
        mediaType: MediaType,
        mediaId: Long,
        isFavorite: Boolean
    ): Result<Unit, NetworkError> {
        return listRepository.setMediaFavorite(
            mediaId = mediaId,
            mediaType = mediaType,
            isFavorite = isFavorite
        )
    }
}