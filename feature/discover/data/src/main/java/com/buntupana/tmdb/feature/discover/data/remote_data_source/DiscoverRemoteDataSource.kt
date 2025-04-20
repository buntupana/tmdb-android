package com.buntupana.tmdb.feature.discover.data.remote_data_source

import com.buntupana.tmdb.core.data.raw.MediaItemRaw
import com.buntupana.tmdb.core.data.raw.MovieItemRaw
import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.buntupana.tmdb.core.data.raw.TvShowItemRaw
import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.LocalDate
import javax.inject.Inject

class DiscoverRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) : RemoteDataSource() {

    suspend fun getMoviesPopular(
        monetizationType: MonetizationType,
        region: String
    ): Result<ResponseListRaw<MovieItemRaw>, NetworkError> {

        val monetizationValue = when (monetizationType) {
            MonetizationType.FREE -> "free"
            MonetizationType.FLAT_RATE -> "flatrate"
            MonetizationType.RENT -> "rent"
        }

        return getResult {
            httpClient.get(urlString = "/3/discover/movie") {
                parameter("with_watch_monetization_types", monetizationValue)
                parameter("watch_region", region)
                parameter("region", region)
            }
        }
    }

    suspend fun getTvShowPopular(
        monetizationType: MonetizationType,
        region: String
    ): Result<ResponseListRaw<TvShowItemRaw>, NetworkError> {

        val monetizationValue = when (monetizationType) {
            MonetizationType.FREE -> "free"
            MonetizationType.FLAT_RATE -> "flatrate"
            MonetizationType.RENT -> "rent"
        }

        return getResult {
            httpClient.get(urlString = "/3/discover/tv") {
                parameter("with_watch_monetization_types", monetizationValue)
                parameter("watch_region", region)
                parameter("region", region)
            }
        }
    }

    suspend fun getTrending(
        trendingType: TrendingType
    ): Result<ResponseListRaw<MediaItemRaw>, NetworkError> {

        val timeWindow = when (trendingType) {
            TrendingType.TODAY -> "day"
            TrendingType.THIS_WEEK -> "week"
        }

        return getResult {
            httpClient.get("/3/trending/all/$timeWindow")
        }
    }

    suspend fun getMoviesInTheatres(
        region: String
    ): Result<ResponseListRaw<MovieItemRaw>, NetworkError> {

        val fromReleaseDate = LocalDate.now()
            .minusMonths(1)
            .minusWeeks(2)
            .toString()
        val toReleaseDate = LocalDate.now().toString()

        return getResult {
            httpClient.get("/3/discover/movie") {
                parameter("watch_region", region)
                parameter("region", region)
                parameter("with_release_type", "3|2")
                parameter("release_date.gte", fromReleaseDate)
                parameter("release_date.lte", toReleaseDate)
                parameter("sort_by", "popularity.desc")
            }
        }
    }

    suspend fun getTvShowsOnAir(): Result<ResponseListRaw<TvShowItemRaw>, NetworkError> {

        return getResult {
            httpClient.get("/3/tv/on_the_air")
        }
    }
}