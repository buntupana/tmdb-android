package com.buntupana.tmdb.feature.account.data.repository

import com.buntupana.tmdb.feature.account.data.mapper.toModel
import com.buntupana.tmdb.feature.account.data.remote_data_source.AccountRemoteDataSource
import com.buntupana.tmdb.feature.account.domain.model.UserCredentials
import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map
import com.panabuntu.tmdb.core.common.model.AccountDetails
import com.panabuntu.tmdb.core.common.provider.UrlProvider

class AccountRepositoryImpl(
    private val accountRemoteDataSource: AccountRemoteDataSource,
    private val urlProvider: UrlProvider
) : AccountRepository {

    override suspend fun createRequestToken(): Result<String, NetworkError> {
        return accountRemoteDataSource.createRequestToken().map { it.requestToken }
    }

    override suspend fun requestAccessToken(requestToken: String): Result<UserCredentials, NetworkError> {
        return accountRemoteDataSource.requestAccessToken(requestToken).map {
            UserCredentials(accessToken = it.accessToken, accountId = it.accountId)
        }
    }

    override suspend fun getSessionId(accessToken: String): Result<String, NetworkError> {
        return accountRemoteDataSource.getSessionId(accessToken).map { it.sessionId }
    }

    override suspend fun deleteSession(accessToken: String): Result<Unit, NetworkError> {
        return accountRemoteDataSource.deleteSession(accessToken)
    }

    override suspend fun getAccountDetails(sessionId: String): Result<AccountDetails, NetworkError> {
        return accountRemoteDataSource.getAccountDetails(sessionId)
            .map { it.toModel(baseUrlAvatar = urlProvider.BASE_URL_AVATAR) }
    }
}