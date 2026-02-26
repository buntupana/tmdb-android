package com.buntupana.tmdb.feature.search.domain.usecase

import com.buntupana.tmdb.feature.search.domain.model.SearchItem
import com.buntupana.tmdb.feature.search.domain.repository.SearchRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.onSuccess

class GetSearchMediaUseCase(
    private val searchRepository: SearchRepository
) {

    suspend operator fun invoke(params: String): Result<List<SearchItem>, NetworkError> {
        val result = searchRepository.getSearchMedia(params)

        result.onSuccess { searchItemList ->
            val resultList = mutableListOf<SearchItem>()

            searchItemList.firstOrNull { it is SearchItem.Movie }?.let {
                it.isHighlighted = true
                resultList.add(it)
            }
            searchItemList.firstOrNull { it is SearchItem.TvShow }?.let {
                it.isHighlighted = true
                resultList.add(it)
            }
            searchItemList.firstOrNull { it is SearchItem.Person }?.let {
                it.isHighlighted = true
                resultList.add(it)
            }

            val remainingItems =
                searchItemList.filterNot { it in resultList }.distinctBy { it.name }
            resultList.addAll(remainingItems)

            return Result.Success(resultList)
        }

        return result
    }
}