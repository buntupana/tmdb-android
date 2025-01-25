package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetWatchlistTotalCountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(): Result<GetMediaItemTotalCountResult, NetworkError> {

        return coroutineScope {
            val movieTotalCountDef = async { accountRepository.getWatchlistMoviesTotalCount() }
            val tvShowTotalCountDef = async { accountRepository.getWatchlistTvShowsTotalCount() }

            val movieTotalCount = movieTotalCountDef.await()
            val tvShowTotalCount = tvShowTotalCountDef.await()

            when {
                movieTotalCount is Result.Error -> movieTotalCount
                tvShowTotalCount is Result.Error -> tvShowTotalCount
                movieTotalCount is Result.Success && tvShowTotalCount is Result.Success -> {
                    Result.Success(
                        GetMediaItemTotalCountResult(
                            movieTotalCount.data,
                            tvShowTotalCount.data
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