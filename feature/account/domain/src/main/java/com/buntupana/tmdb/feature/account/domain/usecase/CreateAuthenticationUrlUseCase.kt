package com.buntupana.tmdb.feature.account.domain.usecase


import com.buntupana.tmdb.feature.account.domain.repository.LoginRepository
import com.panabuntu.tmdb.core.common.api.CoreApi
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map
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