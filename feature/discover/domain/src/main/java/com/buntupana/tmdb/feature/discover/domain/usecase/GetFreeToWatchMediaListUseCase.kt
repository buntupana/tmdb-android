package com.buntupana.tmdb.feature.discover.domain.usecase

import com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import com.panabuntu.tmdb.core.common.entity.Resource
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.usecase.UseCaseResource
import javax.inject.Inject

class GetFreeToWatchMediaListUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
): UseCaseResource<FreeToWatchType, List<MediaItem>>(){

    override suspend fun getSource(params: FreeToWatchType): Resource<List<MediaItem>> {
        return discoverRepository.getMoviesFreeToWatch(params)
    }
}