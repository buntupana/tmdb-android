package com.buntupana.tmdb.feature.seerr.data.remote_data_source

import com.buntupana.tmdb.feature.seerr.data.remote_data_source.raw.SeerrMediaRaw
import com.buntupana.tmdb.feature.seerr.data.remote_data_source.raw.SeerrUserRaw
import com.buntupana.tmdb.feature.seerr.data.remote_data_source.request.CreateMediaRequestBody
import com.buntupana.tmdb.feature.seerr.data.remote_data_source.request.JellyfinLoginRequest
import com.buntupana.tmdb.feature.seerr.data.remote_data_source.request.LocalLoginRequest
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.setCookie
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import timber.log.Timber
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext

class SeerrRemoteDataSource(
    private val httpClient: HttpClient
) {

    suspend fun signInLocal(
        serverUrl: String,
        email: String,
        password: String
    ): Result<LoginResult, NetworkError> = login(
        url = "${serverUrl.trimEnd('/')}/api/v1/auth/local",
        body = LocalLoginRequest(email, password)
    )

    suspend fun signInJellyfin(
        serverUrl: String,
        username: String,
        password: String
    ): Result<LoginResult, NetworkError> = login(
        url = "${serverUrl.trimEnd('/')}/api/v1/auth/jellyfin",
        body = JellyfinLoginRequest(username, password)
    )

    suspend fun signOut(serverUrl: String, cookie: String): Result<Unit, NetworkError> {
        return getResult {
            httpClient.post(urlString = "${serverUrl.trimEnd('/')}/api/v1/auth/logout") {
                header(HttpHeaders.Cookie, cookie)
            }
        }
    }

    suspend fun getMedia(
        serverUrl: String,
        cookie: String,
        mediaType: MediaType,
        mediaId: Long
    ): Result<SeerrMediaRaw, NetworkError> {
        val type = if (mediaType == MediaType.MOVIE) "movie" else "tv"
        return getResult {
            httpClient.get(urlString = "${serverUrl.trimEnd('/')}/api/v1/$type/$mediaId") {
                header(HttpHeaders.Cookie, cookie)
            }
        }
    }

    suspend fun createRequest(
        serverUrl: String,
        cookie: String,
        mediaType: MediaType,
        tmdbId: Long,
        seasons: List<Int>?
    ): Result<Unit, NetworkError> {
        val body = CreateMediaRequestBody(
            mediaType = if (mediaType == MediaType.MOVIE) "movie" else "tv",
            mediaId = tmdbId,
            seasons = seasons
        )
        return getResult {
            httpClient.post(urlString = "${serverUrl.trimEnd('/')}/api/v1/request") {
                header(HttpHeaders.Cookie, cookie)
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    }

    private suspend inline fun <reified B> login(
        url: String,
        body: B
    ): Result<LoginResult, NetworkError> {
        val response = try {
            httpClient.post(urlString = url) {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        } catch (e: UnresolvedAddressException) {
            Timber.e(e); return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            Timber.e(e); return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            Timber.e(e); return Result.Error(NetworkError.UNKNOWN)
        }

        return when (response.status.value) {
            in 200..299 -> try {
                val user = response.body<SeerrUserRaw>()
                val cookie = response.setCookie()
                    .firstOrNull { it.name == "connect.sid" }
                    ?.let { "${it.name}=${it.value}" }
                Result.Success(LoginResult(user = user, cookie = cookie))
            } catch (e: Exception) {
                Timber.e(e); Result.Error(NetworkError.SERIALIZATION)
            }
            401, 403 -> Result.Error(NetworkError.UNAUTHORIZED)
            404 -> Result.Error(NetworkError.NOT_FOUND)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

    private suspend inline fun <reified D> getResult(request: () -> HttpResponse): Result<D, NetworkError> {
        val response = try {
            request()
        } catch (e: UnresolvedAddressException) {
            Timber.e(e); return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            Timber.e(e); return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            Timber.e(e); return Result.Error(NetworkError.UNKNOWN)
        }
        return when (response.status.value) {
            in 200..299 -> try {
                if (D::class == Unit::class) {
                    @Suppress("UNCHECKED_CAST")
                    Result.Success(Unit as D)
                } else Result.Success(response.body<D>())
            } catch (e: Exception) {
                Timber.e(e); Result.Error(NetworkError.SERIALIZATION)
            }
            401, 403 -> Result.Error(NetworkError.UNAUTHORIZED)
            404 -> Result.Error(NetworkError.NOT_FOUND)
            409 -> Result.Error(NetworkError.CONFLICT)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

    data class LoginResult(val user: SeerrUserRaw, val cookie: String?)
}
