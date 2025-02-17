package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import javax.inject.Inject

class AddEpisodeRatingUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) {
    suspend operator fun invoke(
        tvShowId: Long,
        seasonNumber: Int,
        episodeNumber: Int,
        rating: Int?
    ): Result<Unit, NetworkError> {
        return detailRepository.addEpisodeRating(
            tvShowId = tvShowId,
            seasonNumber = seasonNumber,
            episodeNumber = episodeNumber,
            rating = rating
        )
    }
}