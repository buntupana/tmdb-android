package com.buntupana.tmdb.feature.discover.domain.usecase

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.discover.domain.entity.TvShowListFilter
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilteredTvShowsPagingUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
) {
    suspend operator fun invoke(tvShowListFilter: TvShowListFilter): Flow<PagingData<MediaItem.TvShow>> {
        return discoverRepository.getFilteredTvShows(tvShowListFilter)
    }
}