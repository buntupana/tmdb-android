package com.buntupana.tmdb.feature.detail.domain.usecase


import com.buntupana.tmdb.feature.detail.domain.model.SeasonDetail
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.Resource
import javax.inject.Inject

class GetSeasonDetailsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) : com.panabuntu.tmdb.core.common.usecase.UseCaseResourceNew<GetSeasonDetailsUseCaseParams, SeasonDetail>() {
    override suspend fun getSource(params: GetSeasonDetailsUseCaseParams): Resource<SeasonDetail> {
        return detailRepository.getSeasonDetails(params.tvShowId, params.seasonNumber)
    }
}