package com.buntupana.tmdb.feature.account.domain.usecase


import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.UrlProvider
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map
import javax.inject.Inject

class CreateAuthenticationUrlUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val urlProvider: UrlProvider
) {

    suspend operator fun invoke(): Result<String, NetworkError> {
        return accountRepository.createRequestToken().map { requestToken ->
            urlProvider.getSignInUrl(requestToken)
        }
    }
}