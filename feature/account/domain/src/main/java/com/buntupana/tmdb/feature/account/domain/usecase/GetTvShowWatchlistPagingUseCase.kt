package com.buntupana.tmdb.feature.account.domain.usecase

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.model.Order
import kotlinx.coroutines.flow.Flow

class GetTvShowWatchlistPagingUseCase(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(order: Order): Flow<PagingData<MediaItem.TvShow>> {
        return accountRepository.getWatchlistTvShowPaging(order)
    }
}