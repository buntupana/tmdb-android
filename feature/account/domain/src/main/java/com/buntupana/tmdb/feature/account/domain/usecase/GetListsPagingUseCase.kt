package com.buntupana.tmdb.feature.account.domain.usecase

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.account.domain.model.ListItem
import com.buntupana.tmdb.feature.account.domain.repository.ListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListsPagingUseCase @Inject constructor(
    private val listRepository: ListRepository
) {
    suspend operator fun invoke(): Flow<PagingData<ListItem>> {
        return  listRepository.getListsPaging()
    }
}