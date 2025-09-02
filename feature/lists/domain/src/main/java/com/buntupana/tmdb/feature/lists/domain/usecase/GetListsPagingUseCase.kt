package com.buntupana.tmdb.feature.lists.domain.usecase

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import kotlinx.coroutines.flow.Flow

class GetListsPagingUseCase(
    private val listRepository: ListRepository
) {
    suspend operator fun invoke(): Flow<PagingData<UserListDetails>> {
        return  listRepository.getListsPaging()
    }
}