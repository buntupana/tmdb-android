package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result

class SetMediaFavoriteUseCase(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(
        mediaType: MediaType,
        mediaId: Long,
        isFavorite: Boolean
    ): Result<Unit, NetworkError> {
        return accountRepository.setMediaFavorite(
            mediaId = mediaId,
            mediaType = mediaType,
            isFavorite = isFavorite
        )
    }
}