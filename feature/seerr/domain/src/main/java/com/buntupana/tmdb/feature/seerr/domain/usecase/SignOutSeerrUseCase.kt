package com.buntupana.tmdb.feature.seerr.domain.usecase

import com.buntupana.tmdb.feature.seerr.domain.repository.SeerrRepository

class SignOutSeerrUseCase(
    private val repository: SeerrRepository
) {
    suspend operator fun invoke() = repository.signOut()
}
