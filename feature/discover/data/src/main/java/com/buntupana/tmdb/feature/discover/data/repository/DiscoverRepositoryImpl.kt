package com.buntupana.tmdb.feature.discover.data.repository

import com.buntupana.tmdb.core.data.mapper.toModel
import com.buntupana.tmdb.feature.discover.data.remote_data_source.DiscoverRemoteDataSource
import com.buntupana.tmdb.feature.discover.domain.entity.FreeToWatchType
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(
    private val discoverRemoteDataSource: DiscoverRemoteDataSource,
    private val urlProvider: UrlProvider,
    sessionManager: SessionManager
) : DiscoverRepository {

    private val session = sessionManager.session

    override suspend fun getMoviesPopular(monetizationType: MonetizationType): Result<List<MediaItem>, NetworkError> {

        return discoverRemoteDataSource.getMoviesPopular(
            monetizationType = monetizationType,
            region = session.value.countryCode
        )
            .map { result ->
                result.results.map {
                    it.toModel(
                        baseUrlPoster = urlProvider.BASE_URL_POSTER,
                        baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                    )
                }
            }
    }

    override suspend fun getTvShowsPopular(monetizationType: MonetizationType): Result<List<MediaItem>, NetworkError> {

        return discoverRemoteDataSource.getTvShowPopular(
            monetizationType = monetizationType,
            region = session.value.countryCode
        )
            .map { result ->
                result.results.map {
                    it.toModel(
                        baseUrlPoster = urlProvider.BASE_URL_POSTER,
                        baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                    )
                }
            }
    }

    override suspend fun getMoviesInTheatres(): Result<List<MediaItem>, NetworkError> {

        return discoverRemoteDataSource.getMoviesInTheatres(
            region = session.value.countryCode
        )
            .map { result ->
                result.results.map {
                    it.toModel(
                        baseUrlPoster = urlProvider.BASE_URL_POSTER,
                        baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                    )
                }
            }
    }

    override suspend fun getMoviesFreeToWatch(freeToWatchType: FreeToWatchType): Result<List<MediaItem>, NetworkError> {

        return when (freeToWatchType) {
            FreeToWatchType.MOVIES -> {
                discoverRemoteDataSource.getMoviesPopular(
                    monetizationType = MonetizationType.FREE,
                    region = session.value.countryCode
                )
                    .map { result ->
                        result.results.map {
                            it.toModel(
                                baseUrlPoster = urlProvider.BASE_URL_POSTER,
                                baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                            )
                        }
                    }

            }

            FreeToWatchType.TV_SHOWS -> {
                discoverRemoteDataSource.getTvShowPopular(
                    monetizationType = MonetizationType.FREE,
                    region = session.value.countryCode
                )
                    .map { result ->
                        result.results.map {
                            it.toModel(
                                baseUrlPoster = urlProvider.BASE_URL_POSTER,
                                baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                            )
                        }
                    }
            }
        }
    }

    override suspend fun getTrending(trendingType: TrendingType): Result<List<MediaItem>, NetworkError> {

        return discoverRemoteDataSource.getTrending(trendingType)
            .map { result ->
                result.results.toModel(
                    baseUrlPoster = urlProvider.BASE_URL_POSTER,
                    baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                )
            }
    }

    override suspend fun getTvShowOnAir(): Result<List<MediaItem>, NetworkError> {

        return discoverRemoteDataSource.getTvShowsOnAir()
            .map { result ->
                result.results.map {
                    it.toModel(
                        baseUrlPoster = urlProvider.BASE_URL_POSTER,
                        baseUrlBackdrop = urlProvider.BASE_URL_BACKDROP
                    )
                }
            }
    }
}