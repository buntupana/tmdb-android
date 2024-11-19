package com.buntupana.tmdb.feature.discover.domain.repository


import com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.MediaItem

interface DiscoverRepository {

    suspend fun getMoviesFreeToWatch(freeToWatchType: FreeToWatchType): Result<List<MediaItem>, NetworkError>
    suspend fun getTrending(trendingType: TrendingType): Result<List<MediaItem>, NetworkError>
    suspend fun getMoviesPopular(monetizationType: MonetizationType): Result<List<MediaItem>, NetworkError>
    suspend fun getMoviesInTheatres(): Result<List<MediaItem>, NetworkError>
    suspend fun getTvShowOnAir(): Result<List<MediaItem>, NetworkError>
    suspend fun getTvShowsPopular(monetizationType: MonetizationType): Result<List<MediaItem>, NetworkError>
}