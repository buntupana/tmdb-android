package com.buntupana.tmdb.feature.discover.domain.usecase

import com.buntupana.tmdb.app.domain.usecase.UseCaseResource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import javax.inject.Inject

class GetTrendingMediaListUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
): UseCaseResource<TrendingType, List<MediaItem>>(){

    override suspend fun getSource(params: TrendingType): Resource<List<MediaItem>> {
        return discoverRepository.getTrending(params)
    }
}