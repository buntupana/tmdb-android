package com.buntupana.tmdb.feature.discover.data.remote_data_source

import com.buntupana.tmdb.core.data.raw.MediaItemRaw
import com.buntupana.tmdb.core.data.raw.MovieItemRaw
import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.buntupana.tmdb.core.data.raw.TvShowItemRaw
import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.feature.discover.domain.entity.MediaFilter
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.ReleaseType
import com.buntupana.tmdb.feature.discover.domain.entity.SortBy
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

    suspend fun getMovies(
        mediaFilter: MediaFilter,
        region: String,
        page: Int
    ): Result<ResponseListRaw<MovieItemRaw>, NetworkError> {

        val genres = mediaFilter.genreList?.map { it.name.lowercase() }

        val sortBy = when (mediaFilter.sortBy) {
            SortBy.POPULARITY_DESC -> "popularity.desc"
            SortBy.POPULARITY_ASC -> "popularity.asc"
            SortBy.RATING_DESC -> "vote_average.desc"
            SortBy.RATING_ASC -> "vote_average.asc"
            SortBy.RELEASE_DATE_DESC -> "release_date.desc"
            SortBy.RELEASE_DATE_ASC -> "release_date.asc"
            SortBy.TITLE_DESC -> "original_title.desc"
            SortBy.TITLE_ASC -> "original_title.asc"
            null -> "popularity.desc"
        }

        val monetizationValue = when (mediaFilter.monetizationType) {
            MonetizationType.FREE -> "free"
            MonetizationType.FLAT_RATE -> "flatrate"
            MonetizationType.RENT -> "rent"
            null -> null
        }

        val releaseDateFrom = mediaFilter.releaseDateFrom?.toString()
        val releaseDateTo = mediaFilter.releaseDateTo?.toString()

        val releaseTypes = mediaFilter.releaseTypeList?.joinToString("|") {
            when (it) {
                ReleaseType.PREMIER -> "1"
                ReleaseType.THEATRICAL_LIMITED -> "2"
                ReleaseType.THEATRICAL -> "3"
                ReleaseType.DIGITAL -> "4"
                ReleaseType.PHYSICAL -> "5"
                ReleaseType.TV -> "6"
            }
        }

        return getResult {
            httpClient.get(urlString = "/3/discover/movie") {
                parameter("page", page)
                parameter("with_watch_monetization_types", monetizationValue)
                parameter("with_release_type", releaseTypes)
                parameter("with_runtime.lte", mediaFilter.runtime)
                parameter("language", mediaFilter.language)
                parameter("primary_release_date.lte", releaseDateTo)
                parameter("primary_release_date.gte", releaseDateFrom)
                parameter("with_genres", genres?.joinToString(","))
                parameter("sort_by", sortBy)
                parameter("watch_region", region)
                parameter("region", region)
                parameter("vote_count.gte", mediaFilter.minVoteCount)
                parameter("vote_average.gte", mediaFilter.minRating)
                parameter("vote_average.lte", mediaFilter.maxRating)
            }
        }
    }
}