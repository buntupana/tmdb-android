package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.feature.account.domain.model.ListDetail
import com.buntupana.tmdb.feature.account.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import javax.inject.Inject

class GetListDetailsUseCase @Inject constructor(
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(listId: Long): Result<ListDetail, NetworkError> {
        return listRepository.getListDetails(listId)
    }
}