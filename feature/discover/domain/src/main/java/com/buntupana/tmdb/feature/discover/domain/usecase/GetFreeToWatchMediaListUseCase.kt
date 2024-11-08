package com.buntupana.tmdb.feature.discover.domain.usecase

import com.buntupana.tmdb.app.domain.usecase.UseCaseResource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import javax.inject.Inject

class GetFreeToWatchMediaListUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
): UseCaseResource<FreeToWatchType, List<MediaItem>>(){

    override suspend fun getSource(params: FreeToWatchType): Resource<List<MediaItem>> {
        return discoverRepository.getMoviesFreeToWatch(params)
    }
}