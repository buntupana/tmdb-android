package com.buntupana.tmdb.feature.discover.domain.usecase

import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import com.panabuntu.tmdb.core.common.entity.Resource
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.usecase.UseCaseResource
import javax.inject.Inject

class GetTrendingMediaListUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
): UseCaseResource<TrendingType, List<MediaItem>>(){

    override suspend fun getSource(params: TrendingType): Resource<List<MediaItem>> {
        return discoverRepository.getTrending(params)
    }
}