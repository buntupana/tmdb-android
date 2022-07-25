package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.app.domain.usecase.UseCaseResource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.detail.domain.model.MovieDetails
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) : UseCaseResource<Long, MovieDetails>() {

    override suspend fun getSource(params: Long): Resource<MovieDetails> {
        return detailRepository.getMovieDetails(params)
    }
}