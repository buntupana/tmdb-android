package com.buntupana.tmdb.feature.lists.domain.usecase

import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import javax.inject.Inject

class DeleteListUseCase @Inject constructor(
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(listId: Long): Result<Unit, NetworkError> {
        return listRepository.deleteList(listId = listId)
    }
}