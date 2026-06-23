package com.buntupana.tmdb.feature.seer.domain.usecase

import com.buntupana.tmdb.feature.seer.domain.repository.SeerRepository

class SignInSeerLocalUseCase(
    private val repository: SeerRepository
) {
    suspend operator fun invoke(
        serverUrl: String,
        email: String,
        password: String
    ) = repository.signInLocal(serverUrl, email, password)
}
