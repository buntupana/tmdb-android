package com.buntupana.tmdb.feature.detail.data.remote_data_source

import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.feature.detail.data.raw.CreditsMovieRaw
import com.buntupana.tmdb.feature.detail.data.raw.CreditsTvShowRaw
import com.buntupana.tmdb.feature.detail.data.raw.MovieDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.PersonDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.SeasonDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.TvShowDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.TvShowSeasonsDetailsRaw
import com.buntupana.tmdb.feature.detail.data.request.AddRatingRequest
import com.buntupana.tmdb.feature.detail.data.request.FavoriteRequest
import com.buntupana.tmdb.feature.detail.data.request.WatchlistRequest
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class DetailRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) : RemoteDataSource() {

    suspend fun getMovieDetail(
        sessionId: String?,
        movieId: Long
    ): Result<MovieDetailsRaw, NetworkError> {
        return getResult {
            httpClient.get("movie/$movieId") {
                parameter(
                    "append_to_response",
                    "release_dates,videos,credits,recommendations,account_states"
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
            httpClient.get(urlString = "tv/$tvShowId") {
                parameter(
                    "append_to_response",
                    "content_ratings,videos,aggregate_credits,recommendations,account_states"
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
            httpClient.get(urlString = "tv/$tvShowId/season/$seasonNumber")
        }
    }

    suspend fun getMovieCredits(movieId: Long): Result<CreditsMovieRaw, NetworkError> {
        return getResult {
            httpClient.get("movie/$movieId/credits")
        }
    }

    suspend fun getTvCredits(tvShowId: Long): Result<CreditsTvShowRaw, NetworkError> {
        return getResult {
            httpClient.get("tv/$tvShowId/credits")
        }
    }

    suspend fun getPersonDetails(personId: Long): Result<PersonDetailsRaw, NetworkError> {
        return getResult {
            httpClient.get("person/$personId?append_to_response=external_ids,combined_credits")
        }
    }

    suspend fun getTvShowSeasonsDetails(tvShowId: Long): Result<TvShowSeasonsDetailsRaw, NetworkError> {
        return getResult {
            httpClient.get("tv/$tvShowId")
        }
    }

    suspend fun setMediaFavorite(
        accountId: Long,
        mediaId: Long,
        mediaType: MediaType,
        favorite: Boolean
    ): Result<Unit, NetworkError> {
        val mediaTypeStr = when (mediaType) {
            MediaType.MOVIE -> "movie"
            MediaType.TV_SHOW -> "tv"
        }
        return getResult {
            httpClient.post("account/$accountId/favorite") {
                setBody(
                    FavoriteRequest(
                        mediaId = mediaId,
                        mediaType = mediaTypeStr,
                        favorite = favorite
                    )
                )
            }
        }
    }

    suspend fun setMediaWatchlist(
        accountId: Long,
        mediaId: Long,
        mediaType: MediaType,
        watchlist: Boolean
    ): Result<Unit, NetworkError> {

        return getResult {
            httpClient.post("account/$accountId/watchlist") {
                setBody(
                    WatchlistRequest(
                        mediaId = mediaId,
                        mediaType = mediaType.value,
                        watchlist = watchlist
                    )
                )
            }
        }
    }

    suspend fun addMediaRating(
        sessionId: String?,
        mediaType: MediaType,
        mediaId: Long,
        value: Int
    ): Result<Unit, NetworkError> {
        return getResult {
            httpClient.post(urlString = "${mediaType.value}/$mediaId/rating") {
                parameter("session_id", sessionId)
                setBody(AddRatingRequest(value = (value / 10).toFloat()))
            }
        }
    }

    suspend fun deleteMediaRating(
        sessionId: String?,
        mediaType: MediaType,
        mediaId: Long,
    ): Result<Unit, NetworkError> {
        return getResult {
            httpClient.delete(urlString = "${mediaType.value}/$mediaId/rating") {
                parameter("session_id", sessionId)
            }
        }
    }
}