package com.buntupana.tmdb.feature.lists.domain.usecase

import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import kotlinx.coroutines.flow.Flow

class GetListTotalCountUseCase(
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(): Flow<Result<Int, NetworkError>> {
        return listRepository.getListTotalCount()
    }
}