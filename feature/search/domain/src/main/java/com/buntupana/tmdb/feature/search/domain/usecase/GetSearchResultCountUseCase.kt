package com.buntupana.tmdb.feature.search.domain.usecase

import com.buntupana.tmdb.app.domain.usecase.UseCaseResource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetSearchResultCountUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) : UseCaseResource<String, GetSearchResultCountUseCase.Result>() {

    override suspend fun getSource(params: String): Resource<Result> {

        return coroutineScope {

            val moviesCountDef = async { searchRepository.getSearchMoviesResultCount(params) }
            val tvShowsCountDef = async { searchRepository.getSearchTvShowsResultCount(params) }
            val personsCountDef = async { searchRepository.getSearchPersonsCount(params) }

            val moviesCountRes = moviesCountDef.await()
            if (moviesCountRes is Resource.Error) {
                Resource.Error<Int>(moviesCountRes.message)
            }
            val tvShowsCountRes = tvShowsCountDef.await()
            if (tvShowsCountRes is Resource.Error) {
                Resource.Error<Int>(tvShowsCountRes.message)
            }
            val personsCountRes = personsCountDef.await()
            if (personsCountRes is Resource.Error) {
                Resource.Error<Int>(personsCountRes.message)
            }

            if (moviesCountRes is Resource.Success
                &&
                tvShowsCountRes is Resource.Success
                &&
                personsCountRes is Resource.Success
            ) {
                Resource.Success(
                    Result(
                        moviesCountRes.data,
                        tvShowsCountRes.data,
                        personsCountRes.data
                    )
                )
            } else {
                Resource.Error("")
            }
        }
    }

    data class Result(
        val moviesCount: Int,
        val tvShowsCount: Int,
        val personsCount: Int
    )
}