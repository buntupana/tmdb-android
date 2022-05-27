package com.buntupana.tmdb.feature.discover.data.remote_data_source

import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.discover.data.api.DiscoverApi
import com.buntupana.tmdb.feature.discover.data.raw.MovieItemRaw
import javax.inject.Inject

class DiscoverRemoteDataSource @Inject constructor(
    private val discoverApi: DiscoverApi
) : RemoteDataSource() {

    suspend fun getMoviesPopular(
        monetizationType: MonetizationType
    ): Resource<ResponseListRaw<MovieItemRaw>> {
        return getResourceResult {
            discoverApi.fetchPopularMovies(monetizationType = monetizationType.value)
        }
    }
}