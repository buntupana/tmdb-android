package com.buntupana.tmdb.feature.detail.data.remote_data_source

import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.feature.detail.data.raw.CreditsMovieRaw
import com.buntupana.tmdb.feature.detail.data.raw.CreditsTvShowRaw
import com.buntupana.tmdb.feature.detail.data.raw.MovieDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.PersonDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.SeasonDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.TvShowDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.TvShowSeasonsDetailsRaw
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class DetailRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) : RemoteDataSource() {

    suspend fun getMovieDetail(
        sessionId: String?,
        movieId: Long
    ): Result<MovieDetailsRaw, NetworkError> {
        return getResult {
            httpClient.get("/3/movie/$movieId") {
                parameter(
                    "append_to_response",
                    "release_dates,videos,credits,recommendations,account_states,external_ids"
                )
                if (sessionId != null) {
                    parameter("session_id", sessionId)
                }
            }
        }
    }

    suspend fun getTvShowDetail(
        sessionId: String?,
        tvShowId: Long
    ): Result<TvShowDetailsRaw, NetworkError> {
        return getResult {
            httpClient.get(urlString = "/3/tv/$tvShowId") {
                parameter(
                    "append_to_response",
                    "content_ratings,videos,aggregate_credits,recommendations,account_states,external_ids"
                )
                if (sessionId != null) {
                    parameter("session_id", sessionId)
                }
            }
        }
    }

    suspend fun getSeasonDetail(
        tvShowId: Long,
        seasonNumber: Int
    ): Result<SeasonDetailsRaw, NetworkError> {
        return getResult {
            httpClient.get(urlString = "/3/tv/$tvShowId/season/$seasonNumber")
        }
    }

    suspend fun getMovieCredits(movieId: Long): Result<CreditsMovieRaw, NetworkError> {
        return getResult {
            httpClient.get("/3/movie/$movieId/credits")
        }
    }

    suspend fun getTvCredits(tvShowId: Long): Result<CreditsTvShowRaw, NetworkError> {
        return getResult {
            httpClient.get("/3/tv/$tvShowId/aggregate_credits")
        }
    }

    suspend fun getPersonDetails(personId: Long): Result<PersonDetailsRaw, NetworkError> {
        return getResult {
            httpClient.get("/3/person/$personId?append_to_response=external_ids,combined_credits")
        }
    }

    suspend fun getTvShowSeasonsDetails(tvShowId: Long): Result<TvShowSeasonsDetailsRaw, NetworkError> {
        return getResult {
            httpClient.get("/3/tv/$tvShowId")
        }
    }
}