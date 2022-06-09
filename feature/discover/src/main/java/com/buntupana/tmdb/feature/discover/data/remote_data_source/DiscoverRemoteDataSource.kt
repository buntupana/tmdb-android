package com.buntupana.tmdb.feature.discover.data.remote_data_source

import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.discover.data.api.DiscoverApi
import com.buntupana.tmdb.feature.discover.data.raw.AnyItemRaw
import com.buntupana.tmdb.feature.discover.data.raw.MovieItemRaw
import com.buntupana.tmdb.feature.discover.data.raw.TvShowRaw
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType
import org.threeten.bp.LocalDate
import javax.inject.Inject

class DiscoverRemoteDataSource @Inject constructor(
    private val discoverApi: DiscoverApi
) : RemoteDataSource() {

    companion object {
        private fun getRegion(): String {
            return "FR"
        }

        private const val MONETIZATION_FLAT_RATE = "flatrate"
        private const val MONETIZATION_FREE = "free"
        private const val MONETIZATION_RENT = "rent"
    }

    suspend fun getMoviesPopular(
        popularType: PopularType
    ): Resource<ResponseListRaw<MovieItemRaw>> {

        return when (popularType) {
            PopularType.STREAMING -> {
                getMoviesPopularByMonetization(MONETIZATION_FLAT_RATE, getRegion())
            }
            PopularType.ON_TV -> {
                getMoviesPopularByMonetization(MONETIZATION_FREE, getRegion())
            }
            PopularType.FOR_RENT -> {
                getMoviesPopularByMonetization(MONETIZATION_RENT, getRegion())
            }
            PopularType.IN_THEATRES -> {
                getMoviesPopularInTheatres(getRegion())
            }
        }
    }

    suspend fun getMoviesFreeToWatch(): Resource<ResponseListRaw<MovieItemRaw>> {
        return getMoviesPopularByMonetization(MONETIZATION_FREE, getRegion())
    }

    suspend fun getTvShowFreeToWatch(): Resource<ResponseListRaw<TvShowRaw>> {
        return getTvShowPopularByMonetization(MONETIZATION_FREE, getRegion())
    }

    suspend fun getTrending(trendingType: TrendingType): Resource<ResponseListRaw<AnyItemRaw>> {
        return getResourceResult {
            when (trendingType) {
                TrendingType.TODAY -> discoverApi.fetchTrending("day")
                TrendingType.THIS_WEEK -> discoverApi.fetchTrending("week")
            }
        }
    }

    private suspend fun getMoviesPopularByMonetization(
        monetizationType: String,
        region: String
    ): Resource<ResponseListRaw<MovieItemRaw>> {
        return getResourceResult {
            discoverApi.fetchPopularMovies(
                monetizationType = monetizationType,
                watchRegion = region
            )
        }
    }

    private suspend fun getTvShowPopularByMonetization(
        monetizationType: String,
        region: String
    ): Resource<ResponseListRaw<TvShowRaw>> {
        return getResourceResult {
            discoverApi.fetchPopularTvShow(
                monetizationType = monetizationType,
                watchRegion = region
            )
        }
    }

    private suspend fun getMoviesPopularInTheatres(
        region: String
    ): Resource<ResponseListRaw<MovieItemRaw>> {

        val fromReleaseDate = LocalDate.now()
            .minusMonths(1)
            .minusWeeks(2)
            .toString()
        val toReleaseDate = LocalDate.now().toString()

        return getResourceResult {
            discoverApi.fetchPopularMovies(
                region = region,
                releaseType = "3|2",
                fromReleaseDate = fromReleaseDate,
                toReleaseDate = toReleaseDate,
                sortBy = "popularity.desc"
            )
        }
    }
}