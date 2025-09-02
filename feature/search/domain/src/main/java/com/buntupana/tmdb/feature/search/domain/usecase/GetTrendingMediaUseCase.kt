package com.buntupana.tmdb.feature.search.domain.usecase

import com.buntupana.tmdb.feature.search.domain.model.SearchItem
import com.buntupana.tmdb.feature.search.domain.repository.SearchRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result

class GetTrendingMediaUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(): Result<List<SearchItem>, NetworkError> {
        return searchRepository.getTrendingMedia()
    }
}