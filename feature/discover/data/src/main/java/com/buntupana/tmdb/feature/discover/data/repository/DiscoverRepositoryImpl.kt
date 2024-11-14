package com.buntupana.tmdb.feature.discover.data.repository

import com.buntupana.tmdb.data.mapper.toModel
import com.buntupana.tmdb.feature.discover.data.remote_data_source.DiscoverRemoteDataSource
import com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import com.panabuntu.tmdb.core.common.UrlProvider
import com.panabuntu.tmdb.core.common.entity.Resource
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.networkResult
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(
    private val discoverRemoteDataSource: DiscoverRemoteDataSource,
    private val urlProvider: UrlProvider
) : DiscoverRepository {

    override suspend fun getMoviesPopular(monetizationType: MonetizationType): Resource<List<MediaItem>> {
        return networkResult(
            networkCall = { discoverRemoteDataSource.getMoviesPopular(monetizationType) },
            mapResponse = { response ->
                response.results.map {
                    it.toModel(
                        baseUrlPoster = urlProvider.BASE_URL_POSTER,
                        baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                    )
                }
            }
        )
    }

    override suspend fun getTvShowsPopular(monetizationType: MonetizationType): Resource<List<MediaItem>> {
        return networkResult(
            networkCall = { discoverRemoteDataSource.getTvShowPopular(monetizationType) },
            mapResponse = { response ->
                response.results.map {
                    it.toModel(
                        baseUrlPoster = urlProvider.BASE_URL_POSTER,
                        baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                    )
                }
            }
        )
    }

    override suspend fun getMoviesInTheatres(): Resource<List<MediaItem>> {
        return networkResult(
            networkCall = { discoverRemoteDataSource.getMoviesInTheatres() },
            mapResponse = { response ->
                response.results.map {
                    it.toModel(
                        baseUrlPoster = urlProvider.BASE_URL_POSTER,
                        baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                    )
                }
            }
        )
    }

    override suspend fun getMoviesFreeToWatch(freeToWatchType: FreeToWatchType): Resource<List<MediaItem>> {

        return when (freeToWatchType) {
            FreeToWatchType.MOVIES -> {
                networkResult(
                    networkCall = {
                        discoverRemoteDataSource.getMoviesPopular(MonetizationType.FREE)
                    },
                    mapResponse = { response ->
                        response.results.map {
                            it.toModel(
                                baseUrlPoster = urlProvider.BASE_URL_POSTER,
                                baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                            )
                        }
                    }
                )
            }

            FreeToWatchType.TV_SHOWS -> {
                networkResult(
                    networkCall = {
                        discoverRemoteDataSource.getTvShowPopular(MonetizationType.FREE)
                    },
                    mapResponse = { response ->
                        response.results.map {
                            it.toModel(
                                baseUrlPoster = urlProvider.BASE_URL_POSTER,
                                baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                            )
                        }
                    }
                )
            }
        }
    }

    override suspend fun getTrending(trendingType: TrendingType): Resource<List<MediaItem>> {
        return networkResult(
            networkCall = { discoverRemoteDataSource.getTrending(trendingType) },
            mapResponse = { response ->
                response.results.toModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                )
            }
        )
    }

    override suspend fun getTvShowOnAir(): Resource<List<MediaItem>> {
        return networkResult(
            networkCall = { discoverRemoteDataSource.getTvShowsOnAir() },
            mapResponse = { response ->
                response.results.map {
                    it.toModel(
                        baseUrlPoster = urlProvider.BASE_URL_POSTER,
                        baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                    )
                }
            }
        )
    }
}