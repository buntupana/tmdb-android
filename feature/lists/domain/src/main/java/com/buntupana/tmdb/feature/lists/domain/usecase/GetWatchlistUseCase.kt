package com.buntupana.tmdb.feature.lists.domain.usecase

import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.flow.Flow

class GetWatchlistUseCase(
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(mediaType: MediaType): Flow<Result<List<MediaItem>, NetworkError>> {
        return when (mediaType) {
            MediaType.MOVIE -> listRepository.getWatchlistMovies()
            MediaType.TV_SHOW -> listRepository.getWatchlistTvShows()
        }
    }
}