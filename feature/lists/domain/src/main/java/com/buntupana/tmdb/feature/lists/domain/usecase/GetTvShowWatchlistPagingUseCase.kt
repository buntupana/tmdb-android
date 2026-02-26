package com.buntupana.tmdb.feature.lists.domain.usecase

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.model.Order
import kotlinx.coroutines.flow.Flow

class GetTvShowWatchlistPagingUseCase(
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(order: Order): Flow<PagingData<MediaItem.TvShow>> {
        return listRepository.getWatchlistTvShowPaging(order)
    }
}