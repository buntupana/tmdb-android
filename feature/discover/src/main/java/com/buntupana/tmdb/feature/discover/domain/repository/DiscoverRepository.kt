package com.buntupana.tmdb.feature.discover.domain.repository

import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType

interface DiscoverRepository {

    suspend fun getMoviesPopular(popularType: PopularType): Resource<List<MediaItem>>
    suspend fun getMoviesFreeToWatch(freeToWatchType: FreeToWatchType): Resource<List<MediaItem>>
    suspend fun getTrending(trendingType: TrendingType): Resource<List<MediaItem>>
}