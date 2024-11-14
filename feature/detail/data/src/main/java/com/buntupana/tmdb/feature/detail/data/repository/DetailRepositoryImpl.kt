package com.buntupana.tmdb.feature.detail.data.repository

import com.buntupana.tmdb.feature.detail.data.mapper.toModel
import com.buntupana.tmdb.feature.detail.data.remote_data_source.DetailRemoteDataSource
import com.buntupana.tmdb.feature.detail.domain.model.Certification
import com.buntupana.tmdb.feature.detail.domain.model.CreditPersonItem
import com.buntupana.tmdb.feature.detail.domain.model.Credits
import com.buntupana.tmdb.feature.detail.domain.model.CreditsTvShow
import com.buntupana.tmdb.feature.detail.domain.model.ExternalLink
import com.buntupana.tmdb.feature.detail.domain.model.MovieDetails
import com.buntupana.tmdb.feature.detail.domain.model.PersonDetails
import com.buntupana.tmdb.feature.detail.domain.model.ReleaseDate
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.domain.model.SeasonDetail
import com.buntupana.tmdb.feature.detail.domain.model.TvShowDetails
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.UrlProvider
import com.panabuntu.tmdb.core.common.entity.Resource
import com.panabuntu.tmdb.core.common.networkResult
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailRemoteDataSource: DetailRemoteDataSource,
    private val urlProvider: UrlProvider
) : DetailRepository {

    override suspend fun getMovieDetails(movieId: Long): Resource<MovieDetails> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getMovieDetail(movieId) },
            mapResponse = {
                it.toModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP,
                    baseUrlProfile = urlProvider.BASE_URL_PROFILE
                )
            }
        )
    }

    override suspend fun getTvShowDetails(tvShowId: Long): Resource<TvShowDetails> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getTvShowDetail(tvShowId) },
            mapResponse = {
                it.toModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP,
                    baseUrlProfile = urlProvider.BASE_URL_PROFILE
                )
            }
        )
    }

    override suspend fun getSeasonDetails(
        tvShowId: Long,
        episodeNumber: Int
    ): Resource<SeasonDetail> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getSeasonDetail(tvShowId, episodeNumber) },
            mapResponse = {
                it.toModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                )
            }
        )
    }

    override suspend fun getPersonDetails(personId: Long): Resource<PersonDetails> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getPersonDetails(personId) },
            mapResponse = {
                it.toModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP,
                    baseUrlProfile = urlProvider.BASE_URL_PROFILE,
                    baseUrlImdb = urlProvider.BASE_URL_IMDB,
                    baseUrlFacebook = urlProvider.BASE_URL_FACEBOOK,
                    baseUrlInstagram = urlProvider.BASE_URL_INSTAGRAM,
                    baseUrlX = urlProvider.BASE_URL_X
                )
            }
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
            mapResponse = { response -> response.toModel(baseUrlProfile = urlProvider.BASE_URL_PROFILE) }
        )
    }

    override suspend fun getTvShowCredits(tvShowId: Long): Resource<CreditsTvShow> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getTvCredits(tvShowId) },
            mapResponse = { response -> response.toModel(baseUrlProfile = urlProvider.BASE_URL_PROFILE) }
        )
    }

    override suspend fun getPersonFilmography(personId: Long): Resource<List<CreditPersonItem>> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getPersonFilmography(personId) },
            mapResponse = {
                it.toModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                )
            }
        )
    }

    override suspend fun getPersonExternalLinks(personId: Long): Resource<List<ExternalLink>> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getPersonExternalLinks(personId) },
            mapResponse = {
                it.toModel(
                    baseUrlFacebook = urlProvider.BASE_URL_FACEBOOK,
                    baseUrlInstagram = urlProvider.BASE_URL_INSTAGRAM,
                    baseUrlX = urlProvider.BASE_URL_X
                )
            }
        )
    }

    override suspend fun getTvShowSeasonsDetails(tvShowId: Long): Resource<List<Season>> {
        return networkResult(
            networkCall = { detailRemoteDataSource.getTvShowSeasonsDetails(tvShowId) },
            mapResponse = { response -> response.seasons.toModel(baseUrlPoster = urlProvider.BASE_URL_POSTER) }
        )
    }
}