package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import javax.inject.Inject

class SetMediaWatchListUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(
        mediaType: MediaType,
        mediaId: Long,
        watchlist: Boolean
    ): Result<Unit, NetworkError> {
        return accountRepository.setMediaWatchList(
            mediaId = mediaId,
            mediaType = mediaType,
            watchlist = watchlist
        )
    }
}