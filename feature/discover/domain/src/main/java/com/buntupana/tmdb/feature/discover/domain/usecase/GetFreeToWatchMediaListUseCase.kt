package com.buntupana.tmdb.feature.discover.domain.usecase

import com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.MediaItem
import javax.inject.Inject

class GetFreeToWatchMediaListUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
) {

    suspend operator fun invoke(freeToWatchType: FreeToWatchType): Result<List<MediaItem>, NetworkError> {
        return discoverRepository.getMoviesFreeToWatch(freeToWatchType)
    }
}