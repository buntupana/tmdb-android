package com.buntupana.tmdb.feature.discover.domain.usecase

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.discover.domain.entity.MediaFilter
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilteredMoviesPagingUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
) {
    suspend operator fun invoke(mediaFilter: MediaFilter): Flow<PagingData<MediaItem.Movie>> {
        return discoverRepository.getFilteredMovies(mediaFilter)
    }
}