package com.buntupana.tmdb.feature.discover.data.remote_data_source

import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.discover.data.api.DiscoverApi
import com.buntupana.tmdb.feature.discover.data.raw.MovieItemRaw
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import javax.inject.Inject

class DiscoverRemoteDataSource @Inject constructor(
    private val discoverApi: DiscoverApi
) : RemoteDataSource() {

    suspend fun getMoviesPopular(
        popularType: PopularType
    ): Resource<ResponseListRaw<MovieItemRaw>> {

        var monetizationType: String? = null
        var releaseDate: String? = null

        when (popularType) {
            PopularType.STREAMING -> {
                monetizationType = "flatrate"
            }
            PopularType.ON_TV -> {
                monetizationType = "free"
            }
            PopularType.FOR_RENT -> {
                monetizationType = "rent"
            }
            PopularType.IN_THEATRES -> {
                releaseDate = "3|2"
            }
        }

        return getResourceResult {
            discoverApi.fetchPopularMovies(
                monetizationType = monetizationType,
                releaseType = releaseDate
            )
        }
    }
}