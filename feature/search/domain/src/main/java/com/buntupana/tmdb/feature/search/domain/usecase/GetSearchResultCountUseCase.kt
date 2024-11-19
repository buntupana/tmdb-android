package com.buntupana.tmdb.feature.search.domain.usecase

import com.buntupana.tmdb.feature.search.domain.repository.SearchRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.onError
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetSearchResultCountUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend operator fun invoke(params: String): Result<GetSearchResultCountResult, NetworkError> {

        return coroutineScope {

            val moviesCountDef = async { searchRepository.getSearchMoviesResultCount(params) }
            val tvShowsCountDef = async { searchRepository.getSearchTvShowsResultCount(params) }
            val personsCountDef = async { searchRepository.getSearchPersonsCount(params) }

            val moviesCountRes = moviesCountDef.await()
            moviesCountRes.onError {
                return@coroutineScope Result.Error(it)
            }
            val tvShowsCountRes = tvShowsCountDef.await()
            tvShowsCountRes.onError {
                return@coroutineScope Result.Error(it)
            }
            val personsCountRes = personsCountDef.await()
            personsCountRes.onError {
                return@coroutineScope Result.Error(it)
            }

            if (moviesCountRes is Result.Success
                &&
                tvShowsCountRes is Result.Success
                &&
                personsCountRes is Result.Success
            ) {
                return@coroutineScope Result.Success(
                    GetSearchResultCountResult(
                        moviesCountRes.data,
                        tvShowsCountRes.data,
                        personsCountRes.data
                    )
                )
            }
            Result.Error(NetworkError.UNKNOWN)
        }
    }
}