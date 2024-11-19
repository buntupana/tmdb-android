package com.buntupana.tmdb.feature.detail.data.repository

import com.buntupana.tmdb.feature.detail.data.mapper.toModel
import com.buntupana.tmdb.feature.detail.data.remote_data_source.DetailRemoteDataSource
import com.buntupana.tmdb.feature.detail.domain.model.Credits
import com.buntupana.tmdb.feature.detail.domain.model.CreditsTvShow
import com.buntupana.tmdb.feature.detail.domain.model.MediaAccountState
import com.buntupana.tmdb.feature.detail.domain.model.MovieDetails
import com.buntupana.tmdb.feature.detail.domain.model.PersonDetails
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.domain.model.SeasonDetail
import com.buntupana.tmdb.feature.detail.domain.model.TvShowDetails
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.SessionManager
import com.panabuntu.tmdb.core.common.UrlProvider
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailRemoteDataSource: DetailRemoteDataSource,
    private val urlProvider: UrlProvider,
    private val sessionManager: SessionManager
) : DetailRepository {

    override suspend fun getMovieDetails(movieId: Long): Result<MovieDetails, NetworkError> {
        return detailRemoteDataSource.getMovieDetail(movieId).map {
            it.toModel(
                baseUrlPoster = urlProvider.BASE_URL_POSTER,
                baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP,
                baseUrlProfile = urlProvider.BASE_URL_PROFILE
            )
        }
    }

    override suspend fun getMovieAccountState(movieId: Long): Result<MediaAccountState, NetworkError> {
        return detailRemoteDataSource.getMovieAccountState(
            movieId = movieId,
            sessionId = sessionManager.session.value.sessionId.orEmpty()
        ).map {
            it.toModel()
        }
    }

    override suspend fun getTvShowDetails(tvShowId: Long): Result<TvShowDetails, NetworkError> {

        return detailRemoteDataSource.getTvShowDetail(tvShowId)
            .map {
                it.toModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP,
                    baseUrlProfile = urlProvider.BASE_URL_PROFILE
                )
            }
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
                baseUrlImdb = urlProvider.BASE_URL_IMDB,
                baseUrlFacebook = urlProvider.BASE_URL_FACEBOOK,
                baseUrlInstagram = urlProvider.BASE_URL_INSTAGRAM,
                baseUrlX = urlProvider.BASE_URL_X
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

    override suspend fun setMediaFavorite(
        mediaId: Long,
        mediaType: MediaType,
        favorite: Boolean
    ): Result<Unit, NetworkError> {
        return detailRemoteDataSource.setMediaFavorite(
            accountId = sessionManager.session.value.accountDetails?.id ?: 0,
            mediaId = mediaId,
            mediaType = mediaType,
            favorite = favorite
        )
    }

    override suspend fun setMediaWatchList(
        mediaId: Long,
        mediaType: MediaType,
        favorite: Boolean
    ): Result<Unit, NetworkError> {
        return detailRemoteDataSource.setMediaWatchlist(
            accountId = sessionManager.session.value.accountDetails?.id ?: 0,
            mediaId = mediaId,
            mediaType = mediaType,
            favorite = favorite
        )
    }
}