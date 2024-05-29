package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.app.domain.usecase.UseCaseResource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.detail.domain.model.SeasonDetail
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import javax.inject.Inject

class GetSeasonDetailsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) : UseCaseResource<GetSeasonDetailsUseCaseParams, SeasonDetail>() {
    override suspend fun getSource(params: GetSeasonDetailsUseCaseParams): Resource<SeasonDetail> {
        return detailRepository.getSeasonDetails(params.tvShowId, params.seasonNumber)
    }
}

data class GetSeasonDetailsUseCaseParams(
    val tvShowId: Long,
    val seasonNumber: Int
)