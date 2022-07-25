package com.buntupana.tmdb.feature.detail.data.remote_data_source

import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.detail.data.api.DetailApi
import com.buntupana.tmdb.feature.detail.data.raw.*
import javax.inject.Inject

class DetailRemoteDataSource @Inject constructor(
    private val detailApi: DetailApi
) : RemoteDataSource() {

    suspend fun getMovieDetail(movieId: Long): Resource<MovieDetailsRaw> {
        return getResourceResult { detailApi.getMovieDetails(movieId) }
    }

    suspend fun getTvShowDetail(tvShowId: Long): Resource<TvShowDetailsRaw> {
        return getResourceResult { detailApi.getTvShowDetails(tvShowId) }
    }

    suspend fun getMovieReleaseDateList(movieId: Long): Resource<ReleaseDatesRaw> {
        return getResourceResult { detailApi.getMovieReleaseDates(movieId) }
    }

    suspend fun getTvShowCertificationList(tvShowId: Long): Resource<ContentRatingsRaw> {
        return getResourceResult { detailApi.getTvShowRatings(tvShowId) }
    }

    suspend fun getMovieCredits(movieId: Long): Resource<CreditsRaw> {
        return getResourceResult { detailApi.getMovieCredits(movieId) }
    }

    suspend fun getTvCredits(tvShowId: Long): Resource<CreditsRaw> {
        return getResourceResult { detailApi.getTvShowCredits(tvShowId) }
    }
}