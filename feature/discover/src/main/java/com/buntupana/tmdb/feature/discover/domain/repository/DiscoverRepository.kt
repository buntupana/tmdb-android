package com.buntupana.tmdb.feature.discover.domain.repository

import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.discover.domain.model.MovieItem

interface DiscoverRepository {

    suspend fun getMoviesPopularStreaming(): Resource<List<MovieItem>>
}