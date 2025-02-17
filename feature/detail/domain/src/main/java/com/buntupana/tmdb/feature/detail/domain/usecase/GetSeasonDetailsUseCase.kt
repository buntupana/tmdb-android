package com.buntupana.tmdb.feature.detail.domain.usecase


import com.buntupana.tmdb.feature.detail.domain.model.SeasonDetail
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSeasonDetailsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) {

    suspend operator fun invoke(
        tvShowId: Long,
        seasonNumber: Int
    ): Flow<Result<SeasonDetail, NetworkError>> {
        return detailRepository.getSeasonDetails(tvShowId, seasonNumber)
    }
}