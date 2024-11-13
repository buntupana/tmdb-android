package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.Resource
import javax.inject.Inject

class GetTvShowSeasonsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) : com.panabuntu.tmdb.core.common.usecase.UseCaseResourceNew<Long, List<Season>>() {

    override suspend fun getSource(params: Long): Resource<List<Season>> {
        return detailRepository.getTvShowSeasonsDetails(params)
    }
}