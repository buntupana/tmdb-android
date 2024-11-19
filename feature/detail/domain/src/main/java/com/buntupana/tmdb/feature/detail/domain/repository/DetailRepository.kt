package com.buntupana.tmdb.feature.detail.domain.repository

import com.buntupana.tmdb.feature.detail.domain.model.Credits
import com.buntupana.tmdb.feature.detail.domain.model.CreditsTvShow
import com.buntupana.tmdb.feature.detail.domain.model.MediaAccountState
import com.buntupana.tmdb.feature.detail.domain.model.MovieDetails
import com.buntupana.tmdb.feature.detail.domain.model.PersonDetails
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.domain.model.SeasonDetail
import com.buntupana.tmdb.feature.detail.domain.model.TvShowDetails
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result

interface DetailRepository {
    suspend fun getMovieDetails(movieId: Long): Result<MovieDetails, NetworkError>
    suspend fun getTvShowDetails(tvShowId: Long): Result<TvShowDetails, NetworkError>
    suspend fun getMovieCredits(movieId: Long): Result<Credits, NetworkError>
    suspend fun getTvShowCredits(tvShowId: Long): Result<CreditsTvShow, NetworkError>
    suspend fun getPersonDetails(personId: Long): Result<PersonDetails, NetworkError>
    suspend fun getSeasonDetails(tvShowId: Long, episodeNumber: Int): Result<SeasonDetail, NetworkError>
    suspend fun getTvShowSeasonsDetails(tvShowId: Long): Result<List<Season>, NetworkError>
    suspend fun getMovieAccountState(movieId: Long): Result<MediaAccountState, NetworkError>
    suspend fun setMediaFavorite(
        mediaId: Long,
        mediaType: MediaType,
        favorite: Boolean
    ): Result<Unit, NetworkError>

    suspend fun setMediaWatchList(
        mediaId: Long,
        mediaType: MediaType,
        favorite: Boolean
    ): Result<Unit, NetworkError>
}