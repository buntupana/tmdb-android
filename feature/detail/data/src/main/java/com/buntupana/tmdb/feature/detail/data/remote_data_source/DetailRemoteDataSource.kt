package com.buntupana.tmdb.feature.detail.data.remote_data_source

import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.CreditsMovieRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.CreditsTvShowRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.MediaImagesRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.MovieDetailsRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.PersonDetailsRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.PersonImagesRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.SeasonDetailsRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.TvShowDetailsRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.TvShowSeasonsDetailsRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.request.AddRatingRequest
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class DetailRemoteDataSource(
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
                    "release_dates,videos,credits,recommendations,account_states,external_ids,watch/providers,images"
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
                    "content_ratings,videos,aggregate_credits,recommendations,account_states,external_ids,watch/providers,images"
                )
                if (sessionId != null) {
                    parameter("session_id", sessionId)
                }
            }
        }
    }

    suspend fun addMediaRating(
        sessionId: String?,
        mediaType: MediaType,
        mediaId: Long,
        rating: Int
    ): Result<Unit, NetworkError> {
        return getResult {
            httpClient.post(urlString = "/3/${mediaType.value}/$mediaId/rating") {
                parameter("session_id", sessionId)
                setBody(AddRatingRequest(value = (rating / 10).toFloat()))
            }
        }
    }

    suspend fun deleteMediaRating(
        sessionId: String?,
        mediaType: MediaType,
        mediaId: Long,
    ): Result<Unit, NetworkError> {
        return getResult {
            httpClient.delete(urlString = "/3/${mediaType.value}/$mediaId/rating") {
                parameter("session_id", sessionId)
            }
        }
    }

    suspend fun getSeasonDetail(
        sessionId: String?,
        tvShowId: Long,
        seasonNumber: Int
    ): Result<SeasonDetailsRaw, NetworkError> {
        return getResult {
            httpClient.get(urlString = "/3/tv/$tvShowId/season/$seasonNumber") {
                parameter(
                    "append_to_response",
                    "account_states"
                )
                if (sessionId != null) {
                    parameter("session_id", sessionId)
                }
            }
        }
    }

    suspend fun addEpisodeRating(
        sessionId: String?,
        tvShowId: Long,
        seasonNumber: Int,
        episodeNumber: Int,
        rating: Int
    ): Result<Unit, NetworkError> {
        return getResult {
            httpClient.post(urlString = "/3/tv/$tvShowId/season/$seasonNumber/episode/$episodeNumber/rating") {
                parameter("session_id", sessionId)
                setBody(AddRatingRequest(value = (rating / 10).toFloat()))
            }
        }
    }

    suspend fun deleteMediaRating(
        sessionId: String?,
        tvShowId: Long,
        seasonNumber: Int,
        episodeNumber: Int,
    ): Result<Unit, NetworkError> {
        return getResult {
            httpClient.delete(urlString = "/3/tv/$tvShowId/season/$seasonNumber/episode/$episodeNumber/rating") {
                parameter("session_id", sessionId)
            }
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

    suspend fun getMovieImages(movieId: Long): Result<MediaImagesRaw, NetworkError> {
        return getResult {
            httpClient.get("/3/movie/$movieId/images")
        }
    }

    suspend fun getTvShowImages(tvShowId: Long): Result<MediaImagesRaw, NetworkError> {
        return getResult {
            httpClient.get("/3/tv/$tvShowId/images")
        }
    }

    suspend fun getPersonImages(personId: Long): Result<PersonImagesRaw, NetworkError> {
        return getResult {
            httpClient.get("/3/person/$personId/images")
        }
    }
}