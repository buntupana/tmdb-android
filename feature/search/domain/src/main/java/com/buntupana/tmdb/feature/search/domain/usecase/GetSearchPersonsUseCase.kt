package com.buntupana.tmdb.feature.search.domain.usecase

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.search.domain.repository.SearchRepository
import com.panabuntu.tmdb.core.common.model.PersonItem
import kotlinx.coroutines.flow.Flow

class GetSearchPersonsUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(searchKey: String): Flow<PagingData<PersonItem>> {
        return searchRepository.getSearchPersons(searchKey)
    }
}