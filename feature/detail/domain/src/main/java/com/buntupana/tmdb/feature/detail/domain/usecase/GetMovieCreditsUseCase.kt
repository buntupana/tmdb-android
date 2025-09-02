package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map

class GetMovieCreditsUseCase(
    private val detailRepository: DetailRepository
) {

    suspend operator fun invoke(movieId: Long): Result<GetCreditsUseCaseResult, NetworkError> {
        return detailRepository.getMovieCredits(movieId).map {
            GetCreditsUseCaseResult(
                personCastList = it.castList,
                personCrewMap = it.crewList.groupBy { it.department }.toSortedMap()
            )
        }
    }
}