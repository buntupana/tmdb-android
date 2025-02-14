package com.buntupana.tmdb.feature.lists.domain.usecase

import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import javax.inject.Inject

class CreateListUseCase @Inject constructor(
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(
        name: String,
        description: String,
        isPublic: Boolean
    ): Result<Unit, NetworkError> {
        return listRepository.createList(
            name = name,
            description = description,
            isPublic = isPublic
        )
    }
}