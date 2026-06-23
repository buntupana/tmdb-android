package com.buntupana.tmdb.feature.seerr.domain.usecase

import com.buntupana.tmdb.feature.seerr.domain.repository.SeerrRepository

class SignInSeerrJellyfinUseCase(
    private val repository: SeerrRepository
) {
    suspend operator fun invoke(
        serverUrl: String,
        username: String,
        password: String
    ) = repository.signInJellyfin(serverUrl, username, password)
}
