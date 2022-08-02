package com.buntupana.tmdb.feature.detail.data.repository

import com.buntupana.tmdb.core.data.networkResult
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.detail.data.mapper.toModel
import com.buntupana.tmdb.feature.detail.data.remote_data_source.DetailRemoteDataSource
import com.buntupana.tmdb.feature.detail.domain.model.*
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

    override suspend fun getTvShowDetails(tvShowId: Long): Resource<TvShowDetails> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getTvShowDetail(tvShowId) },
            mapResponse = { it.toModel() }
        )
    }

    override suspend fun getPersonDetails(personId: Long): Resource<PersonDetails> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getPersonDetails(personId) },
            mapResponse = { it.toModel() }
        )
    }

    override suspend fun getMovieReleaseDates(movieId: Long): Resource<List<ReleaseDate>> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getMovieReleaseDateList(movieId) },
            mapResponse = { response -> response.results.map { it.toModel() } }
        )
    }

    override suspend fun getTvShowCertificationList(tvShowId: Long): Resource<List<Certification>> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getTvShowCertificationList(tvShowId) },
            mapResponse = { response -> response.results.map { it.toModel() } }
        )
    }

    override suspend fun getMovieCredits(movieId: Long): Resource<Credits> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getMovieCredits(movieId) },
            mapResponse = { response -> response.toModel() }
        )
    }

    override suspend fun getTvShowCredits(tvShowId: Long): Resource<Credits> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getTvCredits(tvShowId) },
            mapResponse = { response -> response.toModel() }
        )
    }

    override suspend fun getPersonFilmography(personId: Long): Resource<List<CreditPersonItem>> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getPersonFilmography(personId) },
            mapResponse = { it.toModel() }
        )
    }

    override suspend fun getPersonExternalLinks(personId: Long): Resource<ExternalLinks> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getPersonExternalLinks(personId) },
            mapResponse = { it.toModel() }
        )
    }
}