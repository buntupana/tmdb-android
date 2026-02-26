package com.buntupana.tmdb.feature.lists.domain.usecase

import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result

class DeleteItemListUseCase(
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(
        listId: Long,
        mediaId: Long,
        mediaType: MediaType
    ): Result<Unit, NetworkError> {
        return listRepository.deleteMediaItemList(
            listId = listId,
            mediaId = mediaId,
            mediaType = mediaType
        )
    }
}