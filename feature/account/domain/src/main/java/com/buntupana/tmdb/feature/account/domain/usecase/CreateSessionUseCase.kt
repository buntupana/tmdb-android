package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.core.domain.entity.NetworkError
import com.buntupana.tmdb.core.domain.entity.Result
import com.buntupana.tmdb.core.domain.entity.asEmptyDataResult
import com.buntupana.tmdb.feature.account.domain.repository.LoginRepository
import timber.log.Timber
import javax.inject.Inject

class CreateSessionUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(requestToken: String): Result<Unit, NetworkError> {

        val createSessionRes = loginRepository.createSession(requestToken)

        if (createSessionRes is Result.Success) {

            val sessionId = createSessionRes.data

            val getAccountRes = loginRepository.getAccountDetails(sessionId)

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