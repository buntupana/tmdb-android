package com.buntupana.tmdb.feature.search.domain.usecase

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.search.domain.repository.SearchRepository
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchMoviesUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(searchKey: String): Flow<PagingData<MediaItem.Movie>> {
        return searchRepository.getSearchMovies(searchKey)
    }
}