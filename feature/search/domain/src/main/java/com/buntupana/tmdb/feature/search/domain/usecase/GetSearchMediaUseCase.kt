package com.buntupana.tmdb.feature.search.domain.usecase

import com.buntupana.tmdb.feature.search.domain.model.SearchItem
import com.buntupana.tmdb.feature.search.domain.repository.SearchRepository
import com.panabuntu.tmdb.core.common.entity.Resource
import com.panabuntu.tmdb.core.common.usecase.UseCaseResource
import javax.inject.Inject

class GetSearchMediaUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) : UseCaseResource<String, List<SearchItem>>() {

    override suspend fun getSource(params: String): Resource<List<SearchItem>> {
        val result = searchRepository.getSearchMedia(params)

        if (result is Resource.Success) {
            val resultList = mutableListOf<SearchItem>()

            result.data.firstOrNull { it is SearchItem.Movie }?.let {
                it.isHighlighted = true
                resultList.add(it)
            }
            result.data.firstOrNull { it is SearchItem.TvShow }?.let {
                it.isHighlighted = true
                resultList.add(it)
            }
            result.data.firstOrNull { it is SearchItem.Person }?.let {
                it.isHighlighted = true
                resultList.add(it)
            }

            val remainingItems = result.data.filterNot { it in resultList }.distinctBy { it.name }
            resultList.addAll(remainingItems)

            return Resource.Success(resultList)
        }

        return result
    }
}