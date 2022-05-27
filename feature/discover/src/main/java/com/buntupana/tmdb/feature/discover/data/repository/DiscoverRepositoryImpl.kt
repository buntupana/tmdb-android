package com.buntupana.tmdb.feature.discover.data.repository

import com.buntupana.tmdb.core.data.networkResult
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.discover.data.mapper.toModel
import com.buntupana.tmdb.feature.discover.data.remote_data_source.DiscoverRemoteDataSource
import com.buntupana.tmdb.feature.discover.data.remote_data_source.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.model.MovieItem
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(
    private val discoverRemoteDataSource: DiscoverRemoteDataSource
) : DiscoverRepository {

    override suspend fun getMoviesPopularStreaming(): Resource<List<MovieItem>> {
        return networkResult(
            networkCall = { discoverRemoteDataSource.getMoviesPopular(MonetizationType.FLAT_RATE) },
            mapResponse = { response -> response.results.map { it.toModel() } }
        )
    }
}