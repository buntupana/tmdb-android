package com.buntupana.tmdb.feature.detail.domain.repository

import com.buntupana.tmdb.feature.detail.domain.model.CreditsMovie
import com.buntupana.tmdb.feature.detail.domain.model.CreditsTvShow
import com.buntupana.tmdb.feature.detail.domain.model.MovieDetails
import com.buntupana.tmdb.feature.detail.domain.model.PersonDetails
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.domain.model.SeasonDetail
import com.buntupana.tmdb.feature.detail.domain.model.TvShowDetails
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getMovieDetails(movieId: Long): Flow<Result<MovieDetails, NetworkError>>

    suspend fun getTvShowDetails(tvShowId: Long): Flow<Result<TvShowDetails, NetworkError>>

    suspend fun addMediaRating(
        mediaType: MediaType,
        mediaId: Long,
        value: Int?
    ): Result<Unit, NetworkError>

    suspend fun getMovieCredits(movieId: Long): Result<CreditsMovie, NetworkError>

    suspend fun getTvShowCredits(tvShowId: Long): Result<CreditsTvShow, NetworkError>

    suspend fun getPersonDetails(personId: Long): Result<PersonDetails, NetworkError>

    suspend fun getSeasonDetails(tvShowId: Long, seasonNumber: Int): Flow<Result<SeasonDetail, NetworkError>>

    suspend fun getTvShowSeasonsDetails(tvShowId: Long): Result<List<Season>, NetworkError>

    suspend fun addEpisodeRating(
        tvShowId: Long,
        seasonNumber: Int,
        episodeNumber: Int,
        rating: Int?
    ): Result<Unit, NetworkError>
}