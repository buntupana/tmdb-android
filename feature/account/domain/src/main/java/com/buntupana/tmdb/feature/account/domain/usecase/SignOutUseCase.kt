package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.onSuccess
import com.panabuntu.tmdb.core.common.manager.SessionManager

class SignOutUseCase(
    private val accountRepository: AccountRepository,
    private val sessionManager: SessionManager
) {

    suspend operator fun invoke(): Result<Unit, NetworkError> {

        if (sessionManager.session.value.isLogged.not()) {
            return Result.Success(Unit)
        }

        return accountRepository.deleteSession(sessionManager.session.value.accessToken!!)
            .onSuccess { sessionManager.clearSession() }
    }
}