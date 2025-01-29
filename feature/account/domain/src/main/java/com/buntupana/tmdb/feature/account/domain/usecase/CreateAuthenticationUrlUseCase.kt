package com.buntupana.tmdb.feature.account.domain.usecase


import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import javax.inject.Inject

class CreateAuthenticationUrlUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val sessionManager: SessionManager,
    private val urlProvider: UrlProvider
) {

    suspend operator fun invoke(): Result<String, NetworkError> {

        sessionManager.removeRequestToken()

        return accountRepository.createRequestToken().map { requestToken ->
            sessionManager.saveRequestToken(requestToken)
            urlProvider.getSignInUrl(requestToken)
        }
    }
}