package com.buntupana.tmdb.feature.search.domain.usecase

import com.buntupana.tmdb.app.domain.usecase.UseCaseResource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.search.domain.model.SearchItem
import com.buntupana.tmdb.feature.search.domain.repository.SearchRepository
import javax.inject.Inject

class GetTrendingMediaUseCase @Inject constructor(
    private val searchRepository: SearchRepository
): UseCaseResource<Unit, List<SearchItem>>(){

    override suspend fun getSource(params: Unit): Resource<List<SearchItem>> {
        return searchRepository.getTrendingMedia()
    }
}