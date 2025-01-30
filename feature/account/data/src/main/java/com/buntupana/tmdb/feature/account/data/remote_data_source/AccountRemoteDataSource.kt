package com.buntupana.tmdb.feature.account.data.remote_data_source


import com.buntupana.tmdb.core.data.mapper.toApi
import com.buntupana.tmdb.core.data.raw.MovieItemRaw
import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.buntupana.tmdb.core.data.raw.TvShowItemRaw
import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.feature.account.data.raw.AccountDetailsRaw
import com.buntupana.tmdb.feature.account.data.raw.CreateAccessTokenRaw
import com.buntupana.tmdb.feature.account.data.raw.CreateRequestTokenRaw
import com.buntupana.tmdb.feature.account.data.raw.CreateSessionRaw
import com.buntupana.tmdb.feature.account.data.raw.ListItemRaw
import com.buntupana.tmdb.feature.account.data.request.AddRatingRequest
import com.buntupana.tmdb.feature.account.data.request.CreateRequestTokenRequest
import com.buntupana.tmdb.feature.account.data.request.CreateSessionRequest
import com.buntupana.tmdb.feature.account.data.request.DeleteSessionRequest
import com.buntupana.tmdb.feature.account.data.request.FavoriteRequest
import com.buntupana.tmdb.feature.account.data.request.RequestAccessTokenRequest
import com.buntupana.tmdb.feature.account.data.request.WatchlistRequest
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.Order
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Named

class AccountRemoteDataSource @Inject constructor(
    @Named("ApiV4") private val httpClient: HttpClient,
    private val httpClientApi3: HttpClient,
    private val urlProvider: UrlProvider
) : RemoteDataSource() {

    suspend fun createRequestToken(): Result<CreateRequestTokenRaw, NetworkError> {
        return getResult<CreateRequestTokenRaw> {
            httpClient.post(urlString = "auth/request_token") {
                setBody(CreateRequestTokenRequest(redirectTo = urlProvider.SIGN_IN_DEEP_LINK_REDIRECT))
            }
        }
    }

    suspend fun requestAccessToken(requestToken: String): Result<CreateAccessTokenRaw, NetworkError> {
        return getResult<CreateAccessTokenRaw> {
            httpClient.post(urlString = "auth/access_token") {
                setBody(RequestAccessTokenRequest(requestToken))
            }
        }
    }

    suspend fun deleteSession(accessToken: String): Result<Unit, NetworkError> {
        return getResult {
            httpClient.delete(urlString = "auth/access_token") {
                contentType(ContentType.Application.Json)
                setBody(DeleteSessionRequest(accessToken))
            }
        }
    }

    suspend fun getSessionId(accessToken: String): Result<CreateSessionRaw, NetworkError> {
        return getResult<CreateSessionRaw> {
            httpClientApi3.post(urlString = "authentication/session/convert/4") {
                setBody(CreateSessionRequest(accessToken))
            }
        }
    }

    suspend fun getAccountDetails(sessionId: String): Result<AccountDetailsRaw, NetworkError> {
        return getResult<AccountDetailsRaw> {
            httpClientApi3.get(urlString = "account") {
                parameter("session_id", sessionId)
            }
        }
    }

    suspend fun getWatchlistMovies(
        accountObjectId: String,
        order: Order = Order.DESC,
        page: Int = 1
    ): Result<ResponseListRaw<MovieItemRaw>, NetworkError> {
        return getResult<ResponseListRaw<MovieItemRaw>> {
            httpClient.get(urlString = "account/$accountObjectId/movie/watchlist") {
                parameter("page", page)
                parameter("sort_by", "created_at.${order.toApi()}")
            }
        }
    }

    suspend fun getWatchlistTvShows(
        accountObjectId: String,
        order: Order = Order.DESC,
        page: Int = 1
    ): Result<ResponseListRaw<TvShowItemRaw>, NetworkError> {
        return getResult<ResponseListRaw<TvShowItemRaw>> {
            httpClient.get(urlString = "account/$accountObjectId/tv/watchlist") {
                parameter("page", page)
                parameter("sort_by", "created_at.${order.toApi()}")
            }
        }
    }

    suspend fun getFavoriteMovies(
        accountObjectId: String,
        order: Order = Order.DESC,
        page: Int = 1
    ): Result<ResponseListRaw<MovieItemRaw>, NetworkError> {
        return getResult<ResponseListRaw<MovieItemRaw>> {
            httpClient.get(urlString = "account/$accountObjectId/movie/favorites") {
                parameter("page", page)
                parameter("sort_by", "created_at.${order.toApi()}")
            }
        }
    }

    suspend fun getFavoriteTvShows(
        accountObjectId: String,
        order: Order = Order.DESC,
        page: Int = 1
    ): Result<ResponseListRaw<TvShowItemRaw>, NetworkError> {
        return getResult<ResponseListRaw<TvShowItemRaw>> {
            httpClient.get(urlString = "account/$accountObjectId/tv/favorites") {
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
            httpClientApi3.post("account/$accountId/favorite") {
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
            httpClientApi3.post("account/$accountId/watchlist") {
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
            httpClientApi3.post(urlString = "${mediaType.value}/$mediaId/rating") {
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
            httpClientApi3.delete(urlString = "${mediaType.value}/$mediaId/rating") {
                parameter("session_id", sessionId)
            }
        }
    }

    suspend fun getLists(
        accountObjectId: String,
        page: Int = 1
    ): Result<ResponseListRaw<ListItemRaw>, NetworkError> {
        return getResult<ResponseListRaw<ListItemRaw>> {
            httpClient.get(urlString = "account/$accountObjectId/lists") {
                parameter("page", page)
            }
        }
    }
}

