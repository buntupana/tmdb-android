package com.buntupana.tmdb.feature.lists.domain.usecase

import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import javax.inject.Inject

class GetListsUseCase @Inject constructor(
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(): Result<List<com.buntupana.tmdb.feature.lists.domain.model.ListItem>, NetworkError> {
        return listRepository.getLists(justFirstPage = true)
    }
}