package com.buntupana.tmdb.feature.discover.domain.repository


import androidx.paging.PagingData
import com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.MovieListFilter
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType
import com.buntupana.tmdb.feature.discover.domain.entity.TvShowListFilter
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {

    suspend fun getMoviesFreeToWatch(freeToWatchType: FreeToWatchType): Result<List<MediaItem>, NetworkError>
    suspend fun getTrending(trendingType: TrendingType): Result<List<MediaItem>, NetworkError>
    suspend fun getMoviesPopular(monetizationType: MonetizationType): Result<List<MediaItem>, NetworkError>
    suspend fun getMoviesInTheatres(): Result<List<MediaItem>, NetworkError>
    suspend fun getTvShowOnAir(): Result<List<MediaItem>, NetworkError>
    suspend fun getTvShowsPopular(monetizationType: MonetizationType): Result<List<MediaItem>, NetworkError>
    suspend fun getFilteredMovies(movieListFilter: MovieListFilter): Flow<PagingData<MediaItem.Movie>>
    suspend fun getFilteredTvShows(tvShowListFilter: TvShowListFilter): Flow<PagingData<MediaItem.TvShow>>
}