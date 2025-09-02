package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result

class AddMediaRatingUseCase (
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(
        mediaType: MediaType,
        mediaId: Long,
        value: Int?
    ): Result<Unit, NetworkError> {
        return accountRepository.addMediaRating(
            mediaType = mediaType,
            mediaId = mediaId,
            value = value
        )
    }
}