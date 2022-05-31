package com.buntupana.tmdb.feature.discover.domain.repository

import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import com.buntupana.tmdb.feature.discover.domain.model.MovieItem

interface DiscoverRepository {

    suspend fun getMoviesPopular(popularType: PopularType): Resource<List<MovieItem>>
}