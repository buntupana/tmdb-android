package com.buntupana.tmdb.feature.seerr.domain.usecase

import com.buntupana.tmdb.feature.seerr.domain.repository.SeerrRepository

class SignInSeerLocalUseCase(
    private val repository: SeerrRepository
) {
    suspend operator fun invoke(
        serverUrl: String,
        email: String,
        password: String
    ) = repository.signInLocal(serverUrl, email, password)
}
