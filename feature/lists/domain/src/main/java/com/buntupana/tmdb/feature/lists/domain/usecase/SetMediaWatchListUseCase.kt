package com.buntupana.tmdb.feature.lists.domain.usecase

import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result

class SetMediaWatchListUseCase(
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(
        mediaType: MediaType,
        mediaId: Long,
        watchlist: Boolean
    ): Result<Unit, NetworkError> {
        return listRepository.setMediaWatchList(
            mediaId = mediaId,
            mediaType = mediaType,
            isWatchlisted = watchlist
        )
    }
}