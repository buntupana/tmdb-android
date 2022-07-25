package com.buntupana.tmdb.feature.detail.domain.repository

import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.detail.domain.model.MovieDetails

interface DetailRepository {
    suspend fun getMovieDetails(movieId: Long): Resource<MovieDetails>
}