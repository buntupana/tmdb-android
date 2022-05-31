package com.buntupana.tmdb.feature.discover.domain.usecase

import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import com.buntupana.tmdb.feature.discover.domain.model.MovieItem
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import com.buntupana.tmdb.tmdb.domain.usecase.UseCaseResource
import javax.inject.Inject

class GetPopularMoviesListUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
) : UseCaseResource<PopularType, List<MovieItem>>() {

    override suspend fun getSource(params: PopularType): Resource<List<MovieItem>> {
        return discoverRepository.getMoviesPopular(params)
    }
}