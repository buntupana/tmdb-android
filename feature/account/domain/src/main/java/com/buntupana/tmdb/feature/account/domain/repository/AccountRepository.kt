package com.buntupana.tmdb.feature.account.domain.repository


import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.AccountDetails

interface AccountRepository {

    suspend fun createRequestToken() : Result<String, NetworkError>

    suspend fun createSession(requestToken: String): Result<String, NetworkError>

    suspend fun getAccountDetails(sessionId: String): Result<AccountDetails, NetworkError>
    suspend fun deleteSession(sessionId: String): Result<Unit, NetworkError>
}