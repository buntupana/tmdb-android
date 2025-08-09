package com.buntupana.tmdb.feature.discover.data.remote_data_source

import com.buntupana.tmdb.core.data.raw.MediaItemRaw
import com.buntupana.tmdb.core.data.raw.MovieItemRaw
import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.buntupana.tmdb.core.data.raw.TvShowItemRaw
import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.feature.discover.domain.entity.Genre
import com.buntupana.tmdb.feature.discover.domain.entity.MediaListFilter
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

        return getResult {
            httpClient.get(urlString = "/3/discover/movie") {
                parameter(
                    key = "with_watch_monetization_types",
                    value = getMonetizationTypeValue(monetizationType)
                )
                parameter("watch_region", region)
                parameter("region", region)
            }
        }
    }

    suspend fun getTvShowPopular(
        monetizationType: MonetizationType,
        region: String
    ): Result<ResponseListRaw<TvShowItemRaw>, NetworkError> {

        return getResult {
            httpClient.get(urlString = "/3/discover/tv") {
                parameter(
                    key = "with_watch_monetization_types",
                    value = getMonetizationTypeValue(monetizationType)
                )
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
        mediaListFilter: MediaListFilter,
        region: String,
        page: Int
    ): Result<ResponseListRaw<MovieItemRaw>, NetworkError> {

        val sortBy = when (mediaListFilter.sortBy) {
            SortBy.POPULARITY_DESC -> "popularity.desc"
            SortBy.POPULARITY_ASC -> "popularity.asc"
            SortBy.RATING_DESC -> "vote_average.desc"
            SortBy.RATING_ASC -> "vote_average.asc"
            SortBy.RELEASE_DATE_DESC -> "release_date.desc"
            SortBy.RELEASE_DATE_ASC -> "release_date.asc"
            SortBy.TITLE_DESC -> "original_title.desc"
            SortBy.TITLE_ASC -> "original_title.asc"
        }

        val releaseDateFrom = mediaListFilter.releaseDateFrom?.toString()
        val releaseDateTo = mediaListFilter.releaseDateTo?.toString()

        val releaseTypes = mediaListFilter.releaseTypeList.joinToString(",") {
            when (it) {
                ReleaseType.PREMIER -> "1"
                ReleaseType.THEATRICAL_LIMITED -> "2"
                ReleaseType.THEATRICAL -> "3"
                ReleaseType.DIGITAL -> "4"
                ReleaseType.PHYSICAL -> "5"
                ReleaseType.TV -> "6"
            }
        }.ifEmpty { null }

        val monetizationTypes =
            mediaListFilter.monetizationTypeList.joinToString("|") {
                getMonetizationTypeValue(it).toString()
            }.ifEmpty { null }

        val genres = mediaListFilter.genreList
            .map { getGenreId(it) }
            .joinToString(",")
            .ifEmpty { null }

        val voteCountMin =
            if (mediaListFilter.minVoteCount == 0 && mediaListFilter.includeNotRated.not()) {
                1
            } else {
                mediaListFilter.minVoteCount
            }

        return getResult {
            httpClient.get(urlString = "/3/discover/movie") {
                parameter("page", page)
                parameter(
                    key = "with_watch_monetization_types",
                    value = monetizationTypes
                )
                parameter("with_release_type", releaseTypes)
                parameter("with_runtime.lte", mediaListFilter.runtime)
                parameter("language", mediaListFilter.language)
                parameter("release_date.lte", releaseDateTo)
                parameter("release_date.gte", releaseDateFrom)
                parameter("with_genres", genres)
                parameter("sort_by", sortBy)
                parameter("watch_region", region)
                parameter("region", region)
                parameter("vote_count.gte", voteCountMin)
                parameter("vote_average.gte", mediaListFilter.userScoreMin / 10)
                parameter("vote_average.lte", mediaListFilter.userScoreMax / 10)
            }
        }
    }

    private fun getMonetizationTypeValue(monetizationType: MonetizationType?): String? {
        return when (monetizationType) {
            MonetizationType.FREE -> "free"
            MonetizationType.FLAT_RATE -> "flatrate"
            MonetizationType.RENT -> "rent"
            MonetizationType.ADS -> "ads"
            MonetizationType.BUY -> "buy"
            null -> null
        }
    }

    private fun getGenreId(genre: Genre): Long {
        return when (genre) {
            Genre.ACTION -> 28
            Genre.ADVENTURE -> 12
            Genre.ANIMATION -> 16
            Genre.COMEDY -> 35
            Genre.CRIME -> 80
            Genre.DOCUMENTARY -> 99
            Genre.DRAMA -> 18
            Genre.FAMILY -> 10
            Genre.FANTASY -> 14
            Genre.HISTORY -> 36
            Genre.HORROR -> 27
            Genre.MUSIC -> 1
            Genre.MYSTERY -> 9648
            Genre.ROMANCE -> 10749
            Genre.SCI_FI -> 878
            Genre.THRILLER -> 53
            Genre.WAR -> 10752
            Genre.WESTERN -> 37
        }
    }
}