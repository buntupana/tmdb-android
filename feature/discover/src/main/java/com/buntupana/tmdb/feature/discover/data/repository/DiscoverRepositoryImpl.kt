package com.buntupana.tmdb.feature.discover.data.repository

import com.buntupana.tmdb.core.data.networkResult
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.feature.discover.data.mapper.toModel
import com.buntupana.tmdb.feature.discover.data.remote_data_source.DiscoverRemoteDataSource
import com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(
    private val discoverRemoteDataSource: DiscoverRemoteDataSource
) : DiscoverRepository {

    override suspend fun getMoviesPopular(popularType: PopularType): Resource<List<MediaItem>> {
        return networkResult(
            networkCall = { discoverRemoteDataSource.getMoviesPopular(popularType) },
            mapResponse = { response -> response.results.map { it.toModel() } }
        )
    }

    override suspend fun getMoviesFreeToWatch(freeToWatchType: FreeToWatchType): Resource<List<MediaItem>> {

        return when (freeToWatchType) {
            FreeToWatchType.MOVIES -> {
                networkResult(
                    networkCall = {
                        discoverRemoteDataSource.getMoviesFreeToWatch()
                    },
                    mapResponse = { response -> response.results.map { it.toModel() } }
                )
            }
            FreeToWatchType.TV_SHOWS -> {
                networkResult(
                    networkCall = {
                        discoverRemoteDataSource.getTvShowFreeToWatch()
                    },
                    mapResponse = { response -> response.results.map { it.toModel() } }
                )
            }
        }
    }

    override suspend fun getTrending(trendingType: TrendingType): Resource<List<MediaItem>> {
        return networkResult(
            networkCall = { discoverRemoteDataSource.getTrending(trendingType) },
            mapResponse = { response -> response.results.map { it.toModel() } }
        )
    }
}