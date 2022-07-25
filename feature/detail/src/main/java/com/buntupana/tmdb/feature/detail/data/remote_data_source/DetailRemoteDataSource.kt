package com.buntupana.tmdb.feature.detail.data.remote_data_source

import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.detail.data.api.DetailApi
import com.buntupana.tmdb.feature.detail.data.raw.MovieDetailsRaw
import javax.inject.Inject

class DetailRemoteDataSource @Inject constructor(
    private val detailApi: DetailApi
) : RemoteDataSource() {

    suspend fun getMovieDetail(movieId: Long): Resource<MovieDetailsRaw> {
        return getResourceResult { detailApi.getMovieDetails(movieId) }
    }
}