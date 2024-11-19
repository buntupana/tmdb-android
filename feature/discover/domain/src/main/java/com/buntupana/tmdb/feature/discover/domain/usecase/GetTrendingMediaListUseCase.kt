package com.buntupana.tmdb.feature.discover.domain.usecase

import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.MediaItem
import javax.inject.Inject

class GetTrendingMediaListUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
) {

    suspend operator fun invoke(trendingType: TrendingType): Result<List<MediaItem>, NetworkError> {
        return discoverRepository.getTrending(trendingType)
    }
}