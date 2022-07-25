package com.buntupana.tmdb.feature.detail.domain.repository

import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails

interface DetailRepository {
    suspend fun getMovieDetails(movieId: Long): Resource<MediaDetails.MovieDetails>
    suspend fun getTvShowDetails(tvShowId: Long): Resource<MediaDetails.TvShowDetails>
}