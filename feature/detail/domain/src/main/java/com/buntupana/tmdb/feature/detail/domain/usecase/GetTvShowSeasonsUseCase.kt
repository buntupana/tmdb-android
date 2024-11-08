package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.core.domain.usecase.UseCaseResourceNew
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import javax.inject.Inject

class GetTvShowSeasonsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) : UseCaseResourceNew<Long, List<Season>>() {

    override suspend fun getSource(params: Long): Resource<List<Season>> {
        return detailRepository.getTvShowSeasonsDetails(params)
    }
}