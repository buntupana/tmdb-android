package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.manager.SessionManager

class ReloadAccountDetailsUseCase(
    private val accountRepository: AccountRepository,
    private val sessionManager: SessionManager
) {

    private val session = sessionManager.session

    suspend operator fun invoke(): Result<Unit, NetworkError> {

        val sessionId = session.value.sessionId

        sessionId ?: return Result.Error(NetworkError.UNAUTHORIZED)

        accountRepository.getAccountDetails(sessionId)

        return when (
            val getAccountRes = accountRepository.getAccountDetails(sessionId)
        ) {
            is Result.Error -> getAccountRes
            is Result.Success -> {

                val newAccountDetails = session.value.accountDetails?.copy(
                    id = getAccountRes.data.id,
                    username = getAccountRes.data.username,
                    name = getAccountRes.data.name,
                    avatarUrl = getAccountRes.data.avatarUrl,
                    countryCode = getAccountRes.data.countryCode
                )

                val newSession = session.value.copy(accountDetails = newAccountDetails)

                sessionManager.saveSession(newSession)
                Result.Success(Unit)
            }
        }
    }
}