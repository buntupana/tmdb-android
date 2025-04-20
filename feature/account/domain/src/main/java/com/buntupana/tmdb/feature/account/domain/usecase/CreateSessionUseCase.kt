package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.model.AccountDetails
import com.panabuntu.tmdb.core.common.model.Session
import javax.inject.Inject

class CreateSessionUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val sessionManager: SessionManager
) {

    suspend operator fun invoke(): Result<Unit, NetworkError> {

        val requestToken = sessionManager
            .getRequestToken() ?: return Result.Error(NetworkError.UNAUTHORIZED)

        val userCredentials = when (
            val requestAccessTokenRes = accountRepository.requestAccessToken(requestToken)
        ) {
            is Result.Error -> return requestAccessTokenRes
            is Result.Success -> requestAccessTokenRes.data
        }

        val sessionId = when (
            val getSessionIdRes = accountRepository.getSessionId(userCredentials.accessToken)
        ) {
            is Result.Error -> return getSessionIdRes
            is Result.Success -> getSessionIdRes.data
        }

        when (
            val getAccountRes = accountRepository.getAccountDetails(sessionId)
        ) {
            is Result.Error -> return getAccountRes
            is Result.Success -> {

                sessionManager.removeRequestToken()

                sessionManager.saveSession(
                    session = Session(
                        accessToken = userCredentials.accessToken,
                        sessionId = sessionId,
                        accountDetails = AccountDetails(
                            id = getAccountRes.data.id,
                            accountObjectId = userCredentials.accountId,
                            username = getAccountRes.data.username,
                            name = getAccountRes.data.name,
                            avatarUrl = getAccountRes.data.avatarUrl,
                            countryCode = getAccountRes.data.countryCode
                        )
                    )
                )
            }
        }
        return Result.Success(Unit)
    }
}