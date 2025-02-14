package com.buntupana.tmdb.feature.lists.domain.usecase

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
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