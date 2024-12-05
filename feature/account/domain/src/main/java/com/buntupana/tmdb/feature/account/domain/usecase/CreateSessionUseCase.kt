package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.asEmptyDataResult
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.model.AccountDetails
import com.panabuntu.tmdb.core.common.model.Session
import javax.inject.Inject

class CreateSessionUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val sessionManager: SessionManager
) {

    suspend operator fun invoke(requestToken: String): Result<Unit, NetworkError> {

        val createSessionRes = accountRepository.createSession(requestToken)

        if (createSessionRes is Result.Success) {

            val sessionId = createSessionRes.data

            val getAccountRes = accountRepository.getAccountDetails(sessionId)

            if (getAccountRes is Result.Success) {
                sessionManager.saveSession(
                    session = Session(
                        sessionId = sessionId,
                        accountDetails = AccountDetails(
                            id = getAccountRes.data.id,
                            username = getAccountRes.data.username,
                            name = getAccountRes.data.name,
                            avatarUrl = getAccountRes.data.avatarUrl
                        )
                    )
                )
            }
            return getAccountRes.asEmptyDataResult()
        }
        return createSessionRes.asEmptyDataResult()
    }
}