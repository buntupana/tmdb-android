package com.buntupana.tmdb.feature.account.data.repository

import com.buntupana.tmdb.core.domain.entity.NetworkError
import com.buntupana.tmdb.core.domain.entity.Result
import com.buntupana.tmdb.core.domain.entity.map
import com.buntupana.tmdb.feature.account.data.mapper.toModel
import com.buntupana.tmdb.feature.account.data.remote_data_source.LoginRemoteDataSource
import com.buntupana.tmdb.feature.account.domain.model.AccountDetails
import com.buntupana.tmdb.feature.account.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource
) : LoginRepository {

    override suspend fun createRequestToken(): Result<String, NetworkError> {
        return loginRemoteDataSource.createRequestToken().map { it.requestToken }
    }

    override suspend fun createSession(requestToken: String): Result<String, NetworkError> {
        return loginRemoteDataSource.createSession(requestToken).map { it.sessionId }
    }

    override suspend fun getAccountDetails(sessionId: String): Result<AccountDetails, NetworkError> {
        return loginRemoteDataSource.getAccountDetails(sessionId).map { it.toModel() }
    }
}