package com.buntupana.tmdb.feature.account.domain.repository

import com.buntupana.tmdb.core.domain.entity.NetworkError
import com.buntupana.tmdb.core.domain.entity.Result
import com.buntupana.tmdb.feature.account.domain.model.AccountDetails

interface LoginRepository {

    suspend fun createRequestToken() : Result<String, NetworkError>

    suspend fun createSession(requestToken: String): Result<String, NetworkError>

    suspend fun getAccountDetails(sessionId: String): Result<AccountDetails, NetworkError>
}