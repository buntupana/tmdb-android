package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.feature.account.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import javax.inject.Inject

class DeleteItemListUseCase @Inject constructor(
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