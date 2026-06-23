package com.buntupana.tmdb.feature.seer.domain.usecase

import com.buntupana.tmdb.feature.seer.domain.repository.SeerRepository

class SignInSeerJellyfinUseCase(
    private val repository: SeerRepository
) {
    suspend operator fun invoke(
        serverUrl: String,
        username: String,
        password: String
    ) = repository.signInJellyfin(serverUrl, username, password)
}
