package com.buntupana.tmdb.feature.account.domain.repository


import com.buntupana.tmdb.feature.account.domain.model.UserCredentials
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.AccountDetails

interface AccountRepository {

    suspend fun createRequestToken() : Result<String, NetworkError>

    suspend fun requestAccessToken(requestToken: String): Result<UserCredentials, NetworkError>

    suspend fun getSessionId(accessToken: String): Result<String, NetworkError>

    suspend fun getAccountDetails(sessionId: String): Result<AccountDetails, NetworkError>

    suspend fun deleteSession(accessToken: String): Result<Unit, NetworkError>
}