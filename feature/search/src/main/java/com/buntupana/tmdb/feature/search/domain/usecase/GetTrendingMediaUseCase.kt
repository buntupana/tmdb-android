package com.buntupana.tmdb.feature.search.domain.usecase

import com.buntupana.tmdb.app.domain.usecase.UseCaseResource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.feature.search.domain.repository.SearchRepository
import javax.inject.Inject

class GetTrendingMediaUseCase @Inject constructor(
    private val searchRepository: SearchRepository
): UseCaseResource<Unit, List<MediaItem>>(){

    override suspend fun getSource(params: Unit): Resource<List<MediaItem>> {
        return searchRepository.getTrendingMedia()
    }
}