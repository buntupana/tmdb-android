package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import javax.inject.Inject

class SetMediaWatchListUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) {

    suspend operator fun invoke(
        mediaType: MediaType,
        mediaId: Long,
        watchlist: Boolean
    ): Result<Unit, NetworkError> {
        return detailRepository.setMediaWatchList(
            mediaId = mediaId,
            mediaType = mediaType,
            watchlist = watchlist
        )
    }
}