package com.buntupana.tmdb.feature.detail.data.repository

import com.buntupana.tmdb.core.data.database.dao.MovieDetailsDao
import com.buntupana.tmdb.core.data.database.dao.TvShowDetailsDao
import com.buntupana.tmdb.core.data.util.getFlowResult
import com.buntupana.tmdb.feature.detail.data.mapper.toEntity
import com.buntupana.tmdb.feature.detail.data.mapper.toModel
import com.buntupana.tmdb.feature.detail.data.remote_data_source.DetailRemoteDataSource
import com.buntupana.tmdb.feature.detail.domain.model.Credits
import com.buntupana.tmdb.feature.detail.domain.model.CreditsTvShow
import com.buntupana.tmdb.feature.detail.domain.model.MovieDetails
import com.buntupana.tmdb.feature.detail.domain.model.PersonDetails
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.domain.model.SeasonDetail
import com.buntupana.tmdb.feature.detail.domain.model.TvShowDetails
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailRemoteDataSource: DetailRemoteDataSource,
    private val movieDetailsDao: MovieDetailsDao,
    private val tvShowDetailsDao: TvShowDetailsDao,
    private val urlProvider: UrlProvider,
    private val sessionManager: SessionManager
) : DetailRepository {

    override suspend fun getMovieDetails(movieId: Long): Flow<Result<MovieDetails, NetworkError>> {

        return getFlowResult(
            networkCall = {
                detailRemoteDataSource.getMovieDetail(
                    sessionId = sessionManager.session.value.sessionId,
                    movieId = movieId
                )
            },
            mapToEntity = {
                it.toEntity()
            },
            updateDataBaseQuery = {
                movieDetailsDao.upsertMovieDetails(it)
            },
            dataBaseQuery = {
                movieDetailsDao.getMovieDetails(movieId)
            },
            mapToModel = {
                it.toModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP,
                    baseUrlProfile = urlProvider.BASE_URL_PROFILE,
                    baseUrlImdb = urlProvider.BASE_URL_IMDB_MEDIA,
                    baseUrlFacebook = urlProvider.BASE_URL_FACEBOOK,
                    baseUrlInstagram = urlProvider.BASE_URL_INSTAGRAM,
                    baseUrlX = urlProvider.BASE_URL_X,
                    baseUrlTiktok = urlProvider.BASE_URL_TIKTOK
                )
            }
        )
    }

    override suspend fun getTvShowDetails(tvShowId: Long): Flow<Result<TvShowDetails, NetworkError>> {

        return getFlowResult(
            networkCall = {
                detailRemoteDataSource.getTvShowDetail(
                    sessionId = sessionManager.session.value.sessionId,
                    tvShowId = tvShowId
                )
            },
            mapToEntity = {
                it.toEntity()
            },
            updateDataBaseQuery = {
                tvShowDetailsDao.upsertTvShowDetails(it)
            },
            dataBaseQuery = {
                tvShowDetailsDao.getTvShowDetails(tvShowId)
            },
            mapToModel = {
                it.toModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP,
                    baseUrlProfile = urlProvider.BASE_URL_PROFILE,
                    baseUrlImdb = urlProvider.BASE_URL_IMDB_MEDIA,
                    baseUrlFacebook = urlProvider.BASE_URL_FACEBOOK,
                    baseUrlInstagram = urlProvider.BASE_URL_INSTAGRAM,
                    baseUrlX = urlProvider.BASE_URL_X,
                    baseUrlTiktok = urlProvider.BASE_URL_TIKTOK
                )
            }
        )
    }

    override suspend fun getSeasonDetails(
        tvShowId: Long,
        episodeNumber: Int
    ): Result<SeasonDetail, NetworkError> {
        return detailRemoteDataSource.getSeasonDetail(tvShowId, episodeNumber)
            .map {
                it.toModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                )
            }
    }

    override suspend fun getPersonDetails(personId: Long): Result<PersonDetails, NetworkError> {

        return detailRemoteDataSource.getPersonDetails(personId).map {
            it.toModel(
                baseUrlPoster = urlProvider.BASE_URL_POSTER,
                baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP,
                baseUrlProfile = urlProvider.BASE_URL_PROFILE,
                baseUrlImdb = urlProvider.BASE_URL_IMDB_PERSON,
                baseUrlFacebook = urlProvider.BASE_URL_FACEBOOK,
                baseUrlInstagram = urlProvider.BASE_URL_INSTAGRAM,
                baseUrlX = urlProvider.BASE_URL_X,
                baseUrlTiktok = urlProvider.BASE_URL_TIKTOK
            )
        }
    }

    override suspend fun getMovieCredits(movieId: Long): Result<Credits, NetworkError> {
        return detailRemoteDataSource.getMovieCredits(movieId)
            .map { it.toModel(baseUrlProfile = urlProvider.BASE_URL_PROFILE) }
    }

    override suspend fun getTvShowCredits(tvShowId: Long): Result<CreditsTvShow, NetworkError> {
        return detailRemoteDataSource.getTvCredits(tvShowId)
            .map { it.toModel(baseUrlProfile = urlProvider.BASE_URL_PROFILE) }
    }

    override suspend fun getTvShowSeasonsDetails(tvShowId: Long): Result<List<Season>, NetworkError> {
        return detailRemoteDataSource.getTvShowSeasonsDetails(tvShowId)
            .map {
                it.seasons.toModel(baseUrlPoster = urlProvider.BASE_URL_POSTER)
            }
    }
}