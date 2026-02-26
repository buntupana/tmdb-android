package com.buntupana.tmdb.feature.lists.domain.usecase

import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import kotlinx.coroutines.flow.Flow

class GetListDetailsUseCase(
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(listId: Long): Flow<Result<UserListDetails?, NetworkError>> {
        return listRepository.getListDetails(listId)
    }
}