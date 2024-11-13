package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.domain.entity.NetworkError
import com.buntupana.tmdb.core.domain.entity.Result
import com.buntupana.tmdb.core.domain.entity.map
import com.buntupana.tmdb.feature.account.domain.repository.LoginRepository
import javax.inject.Inject

class CreateAuthenticationUrlUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(): Result<String, NetworkError> {
        return loginRepository.createRequestToken().map { requestToken ->
            CoreApi.getSignInUrl(requestToken)
        }
    }
}