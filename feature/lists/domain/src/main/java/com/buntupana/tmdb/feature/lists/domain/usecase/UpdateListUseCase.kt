package com.buntupana.tmdb.feature.lists.domain.usecase

import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result

class UpdateListUseCase(
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(
        listId: Long,
        name: String,
        description: String,
        isPublic: Boolean
    ): Result<Unit, NetworkError> {
        return listRepository.updateList(
            listId = listId,
            name = name,
            description = description,
            isPublic = isPublic
        )
    }
}