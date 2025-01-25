package com.buntupana.tmdb.feature.account.data.remote_data_source


import com.buntupana.tmdb.core.data.mapper.toApi
import com.buntupana.tmdb.core.data.raw.MovieItemRaw
import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.buntupana.tmdb.core.data.raw.TvShowItemRaw
import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.feature.account.data.raw.AccountDetailsRaw
import com.buntupana.tmdb.feature.account.data.raw.CreateRequestTokenRaw
import com.buntupana.tmdb.feature.account.data.raw.CreateSessionRaw
import com.buntupana.tmdb.feature.account.data.request.AddRatingRequest
import com.buntupana.tmdb.feature.account.data.request.CreateSessionRequest
import com.buntupana.tmdb.feature.account.data.request.DeleteSessionRequest
import com.buntupana.tmdb.feature.account.data.request.FavoriteRequest
import com.buntupana.tmdb.feature.account.data.request.WatchlistRequest
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.Order
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class AccountRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) : RemoteDataSource() {

    suspend fun createRequestToken(): Result<CreateRequestTokenRaw, NetworkError> {
        return getResult<CreateRequestTokenRaw> { httpClient.get(urlString = "authentication/token/new") }
    }

    suspend fun createSession(requestToken: String): Result<CreateSessionRaw, NetworkError> {
        return getResult<CreateSessionRaw> {
            httpClient.post(urlString = "authentication/session/new") {
                setBody(CreateSessionRequest(requestToken))
            }
        }
    }

    suspend fun deleteSession(sessionId: String): Result<Unit, NetworkError> {
        return getResult {
            httpClient.delete(urlString = "authentication/session") {
                contentType(ContentType.Application.Json)
                setBody(DeleteSessionRequest(sessionId))
            }
        }
    }

    suspend fun getAccountDetails(sessionId: String): Result<AccountDetailsRaw, NetworkError> {
        return getResult<AccountDetailsRaw> {
            httpClient.get(urlString = "account") {
                parameter("session_id", sessionId)
            }
        }
    }

    suspend fun getWatchlistMovies(
        accountId: Long,
        order: Order = Order.DESC,
        page: Int = 1
    ): Result<ResponseListRaw<MovieItemRaw>, NetworkError> {
        return getResult<ResponseListRaw<MovieItemRaw>> {
            httpClient.get(urlString = "account/$accountId/watchlist/movies") {
                parameter("page", page)
                parameter("sort_by", "created_at.${order.toApi()}")
            }
        }
    }

    suspend fun getWatchlistTvShows(
        accountId: Long,
        order: Order = Order.DESC,
        page: Int = 1
    ): Result<ResponseListRaw<TvShowItemRaw>, NetworkError> {
        return getResult<ResponseListRaw<TvShowItemRaw>> {
            httpClient.get(urlString = "account/$accountId/watchlist/tv") {
                parameter("page", page)
                parameter("sort_by", "created_at.${order.toApi()}")
            }
        }
    }

    suspend fun getFavoriteMovies(
        accountId: Long,
        order: Order = Order.DESC,
        page: Int = 1
    ): Result<ResponseListRaw<MovieItemRaw>, NetworkError> {
        return getResult<ResponseListRaw<MovieItemRaw>> {
            httpClient.get(urlString = "account/$accountId/favorite/movies") {
                parameter("page", page)
                parameter("sort_by", "created_at.${order.toApi()}")
            }
        }
    }

    suspend fun getFavoriteTvShows(
        accountId: Long,
        order: Order = Order.DESC,
        page: Int = 1
    ): Result<ResponseListRaw<TvShowItemRaw>, NetworkError> {
        return getResult<ResponseListRaw<TvShowItemRaw>> {
            httpClient.get(urlString = "account/$accountId/favorite/tv") {
                parameter("page", page)
                parameter("sort_by", "created_at.${order.toApi()}")
            }
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

