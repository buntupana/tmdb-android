package com.buntupana.tmdb.feature.account.data.remote_data_source


import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.feature.account.data.remote_data_source.raw.AccountDetailsRaw
import com.buntupana.tmdb.feature.account.data.remote_data_source.raw.CreateAccessTokenRaw
import com.buntupana.tmdb.feature.account.data.remote_data_source.raw.CreateRequestTokenRaw
import com.buntupana.tmdb.feature.account.data.remote_data_source.raw.CreateSessionRaw
import com.buntupana.tmdb.feature.account.data.remote_data_source.request.CreateRequestTokenRequest
import com.buntupana.tmdb.feature.account.data.remote_data_source.request.CreateSessionRequest
import com.buntupana.tmdb.feature.account.data.remote_data_source.request.DeleteSessionRequest
import com.buntupana.tmdb.feature.account.data.remote_data_source.request.RequestAccessTokenRequest
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AccountRemoteDataSource(
    private val httpClient: HttpClient,
    private val urlProvider: UrlProvider
) : RemoteDataSource() {

    suspend fun createRequestToken(): Result<CreateRequestTokenRaw, NetworkError> {
        return getResult<CreateRequestTokenRaw> {
            httpClient.post(urlString = "/4/auth/request_token") {
                setBody(CreateRequestTokenRequest(redirectTo = urlProvider.SIGN_IN_DEEP_LINK_REDIRECT))
            }
        }
    }

    suspend fun requestAccessToken(requestToken: String): Result<CreateAccessTokenRaw, NetworkError> {
        return getResult<CreateAccessTokenRaw> {
            httpClient.post(urlString = "/4/auth/access_token") {
                setBody(RequestAccessTokenRequest(requestToken))
            }
        }
    }

    suspend fun deleteSession(accessToken: String): Result<Unit, NetworkError> {
        return getResult {
            httpClient.delete(urlString = "/4/auth/access_token") {
                contentType(ContentType.Application.Json)
                setBody(DeleteSessionRequest(accessToken))
            }
        }
    }

    suspend fun getSessionId(accessToken: String): Result<CreateSessionRaw, NetworkError> {
        return getResult<CreateSessionRaw> {
            httpClient.post(urlString = "/3/authentication/session/convert/4") {
                setBody(CreateSessionRequest(accessToken))
            }
        }
    }

    suspend fun getAccountDetails(sessionId: String): Result<AccountDetailsRaw, NetworkError> {
        return getResult<AccountDetailsRaw> {
            httpClient.get(urlString = "/3/account") {
                parameter("session_id", sessionId)
            }
        }
    }

}

