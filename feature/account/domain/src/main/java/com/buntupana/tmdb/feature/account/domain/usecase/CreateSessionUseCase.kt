package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.asEmptyDataResult
import timber.log.Timber
import javax.inject.Inject

class CreateSessionUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(requestToken: String): Result<Unit, NetworkError> {

        val createSessionRes = accountRepository.createSession(requestToken)

        if (createSessionRes is Result.Success) {

            val sessionId = createSessionRes.data

            val getAccountRes = accountRepository.getAccountDetails(sessionId)

            if (getAccountRes is Result.Error) {
                return getAccountRes.asEmptyDataResult()
            } else if (getAccountRes is Result.Success) {
                Timber.d("AccountId = ${getAccountRes.data.id}")
                return getAccountRes.asEmptyDataResult()
            }
        }

        return createSessionRes.asEmptyDataResult()
    }
}