package com.buntupana.tmdb.feature.detail.data.repository

import com.buntupana.tmdb.core.data.networkResult
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.detail.data.mapper.toModel
import com.buntupana.tmdb.feature.detail.data.remote_data_source.DetailRemoteDataSource
import com.buntupana.tmdb.feature.detail.domain.model.MovieDetails
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailRemoteDataSource: DetailRemoteDataSource
) : DetailRepository {

    override suspend fun getMovieDetails(movieId: Long): Resource<MovieDetails> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getMovieDetail(movieId) },
            mapResponse = { it.toModel() }
        )
    }
}