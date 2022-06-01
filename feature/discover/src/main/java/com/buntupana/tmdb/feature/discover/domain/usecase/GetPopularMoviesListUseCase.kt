package com.buntupana.tmdb.feature.discover.domain.usecase

import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import com.buntupana.tmdb.tmdb.domain.usecase.UseCaseResource
import javax.inject.Inject

class GetPopularMoviesListUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
) : UseCaseResource<PopularType, List<MediaItem>>() {

    override suspend fun getSource(params: PopularType): Resource<List<MediaItem>> {
        return discoverRepository.getMoviesPopular(params)
    }
}