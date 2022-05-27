package com.buntupana.tmdb.feature.discover.domain.usecase

import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.discover.domain.model.MovieItem
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import com.buntupana.tmdb.tmdb.domain.usecase.UseCaseResource
import javax.inject.Inject

class GetPopularMoviesStreaming @Inject constructor(
    private val discoverRepository: DiscoverRepository
) : UseCaseResource<Unit, List<MovieItem>>() {

    override suspend fun getSource(params: Unit): Resource<List<MovieItem>> {
        return discoverRepository.getMoviesPopularStreaming()
    }
}