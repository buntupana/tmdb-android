package com.buntupana.tmdb.feature.discover.data.remote_data_source

import androidx.compose.ui.text.intl.Locale
import com.buntupana.tmdb.feature.discover.data.api.DiscoverApi
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType
import com.panabuntu.tmdb.core.common.entity.Resource
import com.panabuntu.tmdb.core.common.remote_data_source.RemoteDataSource
import java.time.LocalDate
import javax.inject.Inject

class DiscoverRemoteDataSource @Inject constructor(
    private val discoverApi: DiscoverApi
) : RemoteDataSource() {

    companion object {
        private fun getRegion(): String {
            return Locale.current.region
        }
    }

    suspend fun getMoviesPopular(
        monetizationType: MonetizationType
    ): Resource<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.MovieItemRaw>> {

        val monetizationValue = when (monetizationType) {
            MonetizationType.FREE -> "free"
            MonetizationType.FLAT_RATE -> "flatrate"
            MonetizationType.RENT -> "rent"
        }

        return getResourceResult {
            discoverApi.fetchPopularMovies(
                monetizationType = monetizationValue,
                watchRegion = getRegion()
            )
        }
    }

    suspend fun getTvShowPopular(
        monetizationType: MonetizationType
    ): Resource<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.TvShowRaw>> {

        val monetizationValue = when (monetizationType) {
            MonetizationType.FREE -> "free"
            MonetizationType.FLAT_RATE -> "flatrate"
            MonetizationType.RENT -> "rent"
        }

        return getResourceResult {
            discoverApi.fetchPopularTvShow(
                monetizationType = monetizationValue,
                watchRegion = getRegion()
            )
        }
    }

    suspend fun getTrending(trendingType: TrendingType): Resource<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.AnyMediaItemRaw>> {
        return getResourceResult {
            when (trendingType) {
                TrendingType.TODAY -> discoverApi.fetchTrending("day")
                TrendingType.THIS_WEEK -> discoverApi.fetchTrending("week")
            }
        }
    }

    suspend fun getMoviesInTheatres(): Resource<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.MovieItemRaw>> {

        val fromReleaseDate = LocalDate.now()
            .minusMonths(1)
            .minusWeeks(2)
            .toString()
        val toReleaseDate = LocalDate.now().toString()

        return getResourceResult {
            discoverApi.fetchPopularMovies(
                region = getRegion(),
                releaseType = "3|2",
                fromReleaseDate = fromReleaseDate,
                toReleaseDate = toReleaseDate,
                sortBy = "popularity.desc"
            )
        }
    }

    suspend fun getTvShowsOnAir(): Resource<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.TvShowRaw>> {
        return getResourceResult {
            discoverApi.fetchTvShowsOnAir()
        }
    }
}