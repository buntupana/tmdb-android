package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.core.domain.usecase.UseCaseResourceNew
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) : UseCaseResourceNew<Long, GetCreditsUseCaseResult>() {
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