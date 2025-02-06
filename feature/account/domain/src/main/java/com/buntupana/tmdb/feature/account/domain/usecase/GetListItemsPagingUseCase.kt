package com.buntupana.tmdb.feature.account.domain.usecase

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.account.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListItemsPagingUseCase @Inject constructor(
    private val listRepository: ListRepository
) {

    operator fun invoke(listId: Long): Flow<PagingData<MediaItem>> {
        return listRepository.getListItems(listId)
    }
}