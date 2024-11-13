package com.buntupana.tmdb.feature.search.domain.usecase

import com.buntupana.tmdb.feature.search.domain.model.SearchItem
import com.buntupana.tmdb.feature.search.domain.repository.SearchRepository
import com.panabuntu.tmdb.core.common.entity.Resource
import com.panabuntu.tmdb.core.common.usecase.UseCaseResource
import javax.inject.Inject

class GetTrendingMediaUseCase @Inject constructor(
    private val searchRepository: SearchRepository
): UseCaseResource<Unit, List<SearchItem>>(){

    override suspend fun getSource(params: Unit): Resource<List<SearchItem>> {
        return searchRepository.getTrendingMedia()
    }
}