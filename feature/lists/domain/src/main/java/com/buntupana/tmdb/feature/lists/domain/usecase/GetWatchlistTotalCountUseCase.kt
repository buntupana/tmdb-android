package com.buntupana.tmdb.feature.lists.domain.usecase

import com.buntupana.tmdb.feature.lists.domain.repository.ListRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetWatchlistTotalCountUseCase(
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(): Flow<Result<GetMediaItemTotalCountResult, NetworkError>> {

        return coroutineScope {
            val movieTotalCountDef = async { listRepository.getWatchlistMoviesTotalCount() }
            val tvShowTotalCountDef = async { listRepository.getWatchlistTvShowsTotalCount() }

            combine(
                movieTotalCountDef.await(),
                tvShowTotalCountDef.await()
            ) { movieTotalCountResult, tvShowTotalCountResult ->
                when {
                    movieTotalCountResult is Result.Error -> movieTotalCountResult
                    tvShowTotalCountResult is Result.Error -> tvShowTotalCountResult
                    movieTotalCountResult is Result.Success && tvShowTotalCountResult is Result.Success -> {
                        Result.Success(
                            GetMediaItemTotalCountResult(
                                movieTotalCountResult.data,
                                tvShowTotalCountResult.data
                            )
                        )
                    }

                    else -> {
                        Result.Success(GetMediaItemTotalCountResult())
                    }
                }
            }

        }
    }
}