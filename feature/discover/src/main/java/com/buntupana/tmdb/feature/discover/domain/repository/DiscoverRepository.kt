package com.buntupana.tmdb.feature.discover.domain.repository

import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType

interface DiscoverRepository {

    suspend fun getMoviesFreeToWatch(freeToWatchType: FreeToWatchType): Resource<List<MediaItem>>
    suspend fun getTrending(trendingType: TrendingType): Resource<List<MediaItem>>
    suspend fun getMoviesPopular(monetizationType: MonetizationType): Resource<List<MediaItem>>
    suspend fun getMoviesInTheatres(): Resource<List<MediaItem>>
    suspend fun getTvShowOnAir(): Resource<List<MediaItem>>
    suspend fun getTvShowsPopular(monetizationType: MonetizationType): Resource<List<MediaItem>>
}