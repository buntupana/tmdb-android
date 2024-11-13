package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.Resource
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) : com.panabuntu.tmdb.core.common.usecase.UseCaseResourceNew<Long, GetCreditsUseCaseResult>() {
    override suspend fun getSource(params: Long): Resource<GetCreditsUseCaseResult> {

        return when (val resource = detailRepository.getMovieCredits(params)) {
            is Resource.Error -> Resource.Error(resource.message)
            is Resource.Success -> {
                Resource.Success(
                    GetCreditsUseCaseResult(
                        personCastList = resource.data.castList,
                        personCrewMap = resource.data.crewList.groupBy { it.department }.toSortedMap()
                    )
                )
            }
        }
    }
}