package com.buntupana.tmdb.feature.lists.domain.usecase

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListsPagingUseCase @Inject constructor(
    private val listRepository: ListRepository
) {
    suspend operator fun invoke(): Flow<PagingData<com.buntupana.tmdb.feature.lists.domain.model.MediaList>> {
        return  listRepository.getListsPaging()
    }
}