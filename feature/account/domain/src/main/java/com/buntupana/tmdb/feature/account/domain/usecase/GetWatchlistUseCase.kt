package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWatchlistUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(mediaType: MediaType): Flow<Result<List<MediaItem>, NetworkError>> {
        return when (mediaType) {
            MediaType.MOVIE -> accountRepository.getWatchlistMovies()
            MediaType.TV_SHOW -> accountRepository.getWatchlistTvShows()
        }
    }
}