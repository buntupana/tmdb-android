package com.buntupana.tmdb.feature.detail.data.repository

import com.buntupana.tmdb.core.data.database.TmdbDataBase
import com.buntupana.tmdb.core.data.util.getFlowResult
import com.buntupana.tmdb.feature.detail.data.mapper.toEntity
import com.buntupana.tmdb.feature.detail.data.mapper.toList
import com.buntupana.tmdb.feature.detail.data.mapper.toModel
import com.buntupana.tmdb.feature.detail.data.mapper.toMovieModel
import com.buntupana.tmdb.feature.detail.data.mapper.toTvShowModel
import com.buntupana.tmdb.feature.detail.data.remote_data_source.DetailRemoteDataSource
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.SeasonDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.CreditsMovie
import com.buntupana.tmdb.feature.detail.domain.model.CreditsTvShow
import com.buntupana.tmdb.feature.detail.domain.model.MediaImages
import com.buntupana.tmdb.feature.detail.domain.model.MovieDetails
import com.buntupana.tmdb.feature.detail.domain.model.PersonDetails
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.domain.model.SeasonDetail
import com.buntupana.tmdb.feature.detail.domain.model.TvShowDetails
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map
import com.panabuntu.tmdb.core.common.entity.onSuccess
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import kotlinx.coroutines.flow.Flow

class DetailRepositoryImpl(
    private val detailRemoteDataSource: DetailRemoteDataSource,
    private val db: TmdbDataBase,
    private val urlProvider: UrlProvider,
    sessionManager: SessionManager
) : DetailRepository {

    private val session = sessionManager.session

    override suspend fun getMovieDetails(movieId: Long): Flow<Result<MovieDetails, NetworkError>> {

        return getFlowResult(
            networkCall = {
                detailRemoteDataSource.getMovieDetail(
                    sessionId = session.value.sessionId,
                    movieId = movieId
                )
            },
            mapToEntity = {
                it.toEntity(session.value.countryCode)
            },
            updateDataBaseQuery = {
                db.mediaDao.upsert(it)
            },
            fetchFromDataBaseQuery = {
                db.mediaDao.get(movieId, MediaType.MOVIE)
            },
            mapToModel = {
                it.toMovieModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP,
                    baseUrlProfile = urlProvider.BASE_URL_PROFILE,
                    baseUrlImdb = urlProvider.BASE_URL_IMDB_MEDIA,
                    baseUrlFacebook = urlProvider.BASE_URL_FACEBOOK,
                    baseUrlInstagram = urlProvider.BASE_URL_INSTAGRAM,
                    baseUrlX = urlProvider.BASE_URL_X,
                    baseUrlTiktok = urlProvider.BASE_URL_TIKTOK,
                    baseUrlProvider = urlProvider.BASE_URL_PROVIDER
                )
            }
        )
    }

    override suspend fun getTvShowDetails(tvShowId: Long): Flow<Result<TvShowDetails, NetworkError>> {

        return getFlowResult(
            networkCall = {
                detailRemoteDataSource.getTvShowDetail(
                    sessionId = session.value.sessionId,
                    tvShowId = tvShowId
                )
            },
            mapToEntity = {
                it.toEntity(session.value.countryCode)
            },
            updateDataBaseQuery = {
                db.mediaDao.upsert(it)
            },
            fetchFromDataBaseQuery = {
                db.mediaDao.get(tvShowId, MediaType.TV_SHOW)
            },
            mapToModel = {
                it.toTvShowModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP,
                    baseUrlProfile = urlProvider.BASE_URL_PROFILE,
                    baseUrlImdb = urlProvider.BASE_URL_IMDB_MEDIA,
                    baseUrlFacebook = urlProvider.BASE_URL_FACEBOOK,
                    baseUrlInstagram = urlProvider.BASE_URL_INSTAGRAM,
                    baseUrlX = urlProvider.BASE_URL_X,
                    baseUrlTiktok = urlProvider.BASE_URL_TIKTOK,
                    baseUrlProvider = urlProvider.BASE_URL_PROVIDER
                )
            }
        )
    }

    override suspend fun addMediaRating(
        mediaType: MediaType,
        mediaId: Long,
        value: Int?
    ): Result<Unit, NetworkError> {

        val result = if (value == null || value == 0) {
            detailRemoteDataSource.deleteMediaRating(
                sessionId = session.value.sessionId,
                mediaType = mediaType,
                mediaId = mediaId
            ).onSuccess {
                db.mediaDao.updateRating(
                    id = mediaId,
                    mediaType = mediaType,
                    rating = value
                )
            }
        } else {
            detailRemoteDataSource.addMediaRating(
                sessionId = session.value.sessionId,
                mediaType = mediaType,
                mediaId = mediaId,
                rating = value
            ).onSuccess {
                db.mediaDao.updateRatingAndWatchlist(
                    id = mediaId,
                    mediaType = mediaType,
                    rating = value
                )
                db.watchlistDao.delete(mediaId, mediaType)
            }
        }

        return result
    }

    override suspend fun getSeasonDetails(
        tvShowId: Long,
        seasonNumber: Int
    ): Flow<Result<SeasonDetail, NetworkError>> {

        var seasonDetailsRaw: SeasonDetailsRaw? = null

        return getFlowResult(
            networkCall = {
                detailRemoteDataSource.getSeasonDetail(
                    sessionId = session.value.sessionId,
                    tvShowId = tvShowId,
                    seasonNumber = seasonNumber
                )
            },
            mapToEntity = {
                seasonDetailsRaw = it
                it.episodes.toEntity(it.accountStates?.results)
            },
            updateDataBaseQuery = {
                db.episodesDao.upsertEpisodes(it)
            },
            fetchFromDataBaseQuery = {
                db.episodesDao.getEpisodes(tvShowId, seasonNumber)
            },
            mapToModel = {
                seasonDetailsRaw!!.toModel(
                    baseUrlProfile = urlProvider.BASE_URL_PROFILE,
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP,
                    episodeEntityList = it
                )
            }
        )
    }

    override suspend fun addEpisodeRating(
        tvShowId: Long,
        seasonNumber: Int,
        episodeNumber: Int,
        rating: Int?
    ): Result<Unit, NetworkError> {

        return if (rating == null || rating == 0) {
            detailRemoteDataSource.deleteMediaRating(
                sessionId = session.value.sessionId,
                tvShowId = tvShowId,
                seasonNumber = seasonNumber,
                episodeNumber = episodeNumber
            ).onSuccess {
                db.episodesDao.updateRating(
                    tvShowId = tvShowId,
                    seasonNumber = seasonNumber,
                    episodeNumber = episodeNumber,
                    rating = null
                )
            }
        } else {
            detailRemoteDataSource.addEpisodeRating(
                sessionId = session.value.sessionId,
                tvShowId = tvShowId,
                seasonNumber = seasonNumber,
                episodeNumber = episodeNumber,
                rating = rating
            ).onSuccess {
                db.episodesDao.updateRating(
                    tvShowId = tvShowId,
                    seasonNumber = seasonNumber,
                    episodeNumber = episodeNumber,
                    rating = rating
                )
            }
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

    override suspend fun getMovieCredits(movieId: Long): Result<CreditsMovie, NetworkError> {
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

    override suspend fun getMediaImages(
        mediaId: Long,
        mediaType: MediaType
    ): Result<MediaImages, NetworkError> {
        return when (mediaType) {
            MediaType.MOVIE -> detailRemoteDataSource.getMovieImages(movieId = mediaId)
            MediaType.TV_SHOW -> detailRemoteDataSource.getTvShowImages(tvShowId = mediaId)
        }.map { it.toModel(urlProvider.BASE_URL_IMAGE_ORIGINAL_SIZE) }
    }

    override suspend fun getPersonImages(personId: Long): Result<List<String>, NetworkError> {
        return detailRemoteDataSource.getPersonImages(personId)
            .map { it.toList(urlProvider.BASE_URL_IMAGE_ORIGINAL_SIZE) }
    }
}