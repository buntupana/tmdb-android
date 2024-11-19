package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import javax.inject.Inject

class GetTvShowSeasonsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) {
    suspend operator fun invoke(tvShowId: Long): Result<List<Season>, NetworkError> {
        return detailRepository.getTvShowSeasonsDetails(tvShowId)
    }
}