package com.buntupana.tmdb.feature.account.domain.usecase

import com.buntupana.tmdb.feature.account.domain.repository.AccountRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetFavoriteTotalCountUseCase(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(): Flow<Result<GetMediaItemTotalCountResult, NetworkError>> {

        return coroutineScope {
            val movieTotalCountDef = async { accountRepository.getFavoriteMoviesTotalCount() }
            val tvShowTotalCountDef = async { accountRepository.getFavoriteTvShowsTotalCount() }

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