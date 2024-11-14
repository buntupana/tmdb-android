package com.buntupana.tmdb.feature.account.data.remote_data_source


import com.buntupana.tmdb.feature.account.data.raw.AccountDetailsRaw
import com.buntupana.tmdb.feature.account.data.raw.CreateRequestTokenRaw
import com.buntupana.tmdb.feature.account.data.raw.CreateSessionRaw
import com.buntupana.tmdb.feature.account.data.request.CreateSessionRequest
import com.buntupana.tmdb.feature.account.data.request.DeleteSessionRequest
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
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
) : com.buntupana.tmdb.data.remote_data_source.RemoteDataSource() {

    suspend fun createRequestToken(): Result<CreateRequestTokenRaw, NetworkError> {
        return getResult<CreateRequestTokenRaw> { httpClient.get(urlString = "authentication/token/new") }
    }

    suspend fun createSession(requestToken: String): Result<CreateSessionRaw, NetworkError> {
        return getResult<CreateSessionRaw> {
            httpClient.post(urlString = "authentication/session/new") {
                contentType(ContentType.Application.Json)
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
}

