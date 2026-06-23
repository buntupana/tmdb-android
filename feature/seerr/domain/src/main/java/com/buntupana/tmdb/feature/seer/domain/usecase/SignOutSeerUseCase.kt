package com.buntupana.tmdb.feature.seer.domain.usecase

import com.buntupana.tmdb.feature.seer.domain.repository.SeerRepository

class SignOutSeerUseCase(
    private val repository: SeerRepository
) {
    suspend operator fun invoke() = repository.signOut()
}
