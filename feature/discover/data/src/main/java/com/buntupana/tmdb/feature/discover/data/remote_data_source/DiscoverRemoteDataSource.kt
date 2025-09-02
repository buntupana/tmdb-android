package com.buntupana.tmdb.feature.discover.data.remote_data_source

import com.buntupana.tmdb.core.data.raw.MediaItemRaw
import com.buntupana.tmdb.core.data.raw.MovieItemRaw
import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.buntupana.tmdb.core.data.raw.TvShowItemRaw
import com.buntupana.tmdb.core.data.remote_data_source.RemoteDataSource
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.MovieGenre
import com.buntupana.tmdb.feature.discover.domain.entity.MovieListFilter
import com.buntupana.tmdb.feature.discover.domain.entity.ReleaseType
import com.buntupana.tmdb.feature.discover.domain.entity.SortBy
import com.buntupana.tmdb.feature.discover.domain.entity.TrendingType
import com.buntupana.tmdb.feature.discover.domain.entity.TvShowGenre
import com.buntupana.tmdb.feature.discover.domain.entity.TvShowListFilter
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.LocalDate

class DiscoverRemoteDataSource(
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
        movieListFilter: MovieListFilter,
        region: String,
        page: Int
    ): Result<ResponseListRaw<MovieItemRaw>, NetworkError> {

        val sortBy = getMovieSortBy(movieListFilter.sortBy)

        val releaseDateFrom = movieListFilter.releaseDateFrom?.toString()
        val releaseDateTo = movieListFilter.releaseDateTo?.toString()

        val releaseTypes = movieListFilter.releaseTypeList.joinToString(",") {
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
            movieListFilter.monetizationTypeList.joinToString("|") {
                getMonetizationTypeValue(it).toString()
            }.ifEmpty { null }

        val genres = movieListFilter.genreList
            .map { getMovieGenreId(it) }
            .joinToString(",")
            .ifEmpty { null }

        val voteCountMin =
            if (movieListFilter.minUserVotes == 0 && movieListFilter.includeNotRated.not()) {
                1
            } else {
                movieListFilter.minUserVotes
            }

        return getResult {
            httpClient.get(urlString = "/3/discover/movie") {
                parameter("page", page)
                parameter(
                    key = "with_watch_monetization_types",
                    value = monetizationTypes
                )
                parameter("with_release_type", releaseTypes)
                parameter("language", movieListFilter.language)
                parameter("release_date.lte", releaseDateTo)
                parameter("release_date.gte", releaseDateFrom)
                parameter("with_genres", genres)
                parameter("sort_by", sortBy)
                parameter("watch_region", region)
                parameter("region", region)
                parameter("vote_count.gte", voteCountMin)
                parameter("vote_average.gte", movieListFilter.userScoreRange.first / 10)
                parameter("vote_average.lte", movieListFilter.userScoreRange.last / 10)
                parameter("with_runtime.gte", movieListFilter.runTimeRange.first)
                parameter("with_runtime.lte", movieListFilter.runTimeRange.last)
            }
        }
    }

    suspend fun getTvShows(
        tvShowListFilter: TvShowListFilter,
        region: String,
        page: Int
    ): Result<ResponseListRaw<TvShowItemRaw>, NetworkError> {

        val sortBy = getTvShowSortBy(tvShowListFilter.sortBy)

        val releaseDateFrom = tvShowListFilter.releaseDateFrom?.toString()
        val releaseDateTo = tvShowListFilter.releaseDateTo?.toString()

        val monetizationTypes =
            tvShowListFilter.monetizationTypeList.joinToString("|") {
                getMonetizationTypeValue(it).toString()
            }.ifEmpty { null }

        val genres = tvShowListFilter.genreList
            .map { getTvShowGenreId(it) }
            .joinToString(",")
            .ifEmpty { null }

        val voteCountMin =
            if (tvShowListFilter.minUserVotes == 0 && tvShowListFilter.includeNotRated.not()) {
                1
            } else {
                tvShowListFilter.minUserVotes
            }

        return getResult {
            httpClient.get(urlString = "/3/discover/tv") {
                parameter("page", page)
                parameter("sort_by", sortBy)
                parameter(
                    key = "with_watch_monetization_types",
                    value = monetizationTypes
                )
                parameter("language", tvShowListFilter.language)
                if (tvShowListFilter.searchFirstAirDate) {
                    parameter("first_air_date.gte", releaseDateFrom)
                    parameter("first_air_date.lte", releaseDateTo)
                } else {
                    parameter("air_date.gte", releaseDateFrom)
                    parameter("air_date.lte", releaseDateTo)
                }
                parameter("with_genres", genres)
                parameter("watch_region", region)
                parameter("region", region)
                parameter("vote_count.gte", voteCountMin)
                parameter("vote_average.gte", tvShowListFilter.userScoreRange.first / 10)
                parameter("vote_average.lte", tvShowListFilter.userScoreRange.last / 10)
                parameter("with_runtime.gte", tvShowListFilter.runTimeRange.first)
                parameter("with_runtime.lte", tvShowListFilter.runTimeRange.last)
            }
        }
    }

    private fun getMovieSortBy(sortBy: SortBy): String {
        return when (sortBy) {
            SortBy.POPULARITY_DESC -> "popularity.desc"
            SortBy.POPULARITY_ASC -> "popularity.asc"
            SortBy.RATING_DESC -> "vote_average.desc"
            SortBy.RATING_ASC -> "vote_average.asc"
            SortBy.RELEASE_DATE_DESC -> "release_date.desc"
            SortBy.RELEASE_DATE_ASC -> "release_date.asc"
            SortBy.TITLE_DESC -> "original_title.desc"
            SortBy.TITLE_ASC -> "original_title.asc"
        }
    }

    private fun getTvShowSortBy(sortBy: SortBy): String {
        return when (sortBy) {
            SortBy.POPULARITY_DESC -> "popularity.desc"
            SortBy.POPULARITY_ASC -> "popularity.asc"
            SortBy.RATING_DESC -> "vote_average.desc"
            SortBy.RATING_ASC -> "vote_average.asc"
            SortBy.RELEASE_DATE_DESC -> "first_air_date.desc"
            SortBy.RELEASE_DATE_ASC -> "first_air_date.asc"
            SortBy.TITLE_DESC -> "original_title.desc"
            SortBy.TITLE_ASC -> "original_title.asc"
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

    private fun getMovieGenreId(movieGenre: MovieGenre): Long {
        return when (movieGenre) {
            MovieGenre.ACTION -> 28
            MovieGenre.ADVENTURE -> 12
            MovieGenre.ANIMATION -> 16
            MovieGenre.COMEDY -> 35
            MovieGenre.CRIME -> 80
            MovieGenre.DOCUMENTARY -> 99
            MovieGenre.DRAMA -> 18
            MovieGenre.FAMILY -> 10
            MovieGenre.FANTASY -> 14
            MovieGenre.HISTORY -> 36
            MovieGenre.HORROR -> 27
            MovieGenre.MUSIC -> 1
            MovieGenre.MYSTERY -> 9648
            MovieGenre.ROMANCE -> 10749
            MovieGenre.SCI_FI -> 878
            MovieGenre.THRILLER -> 53
            MovieGenre.WAR -> 10752
            MovieGenre.WESTERN -> 37
        }
    }

    private fun getTvShowGenreId(tvShowGenre: TvShowGenre): Long {
        return when (tvShowGenre) {
            TvShowGenre.ACTION_AND_ADVENTURE -> 10759
            TvShowGenre.ANIMATION -> 16
            TvShowGenre.COMEDY -> 35
            TvShowGenre.CRIME -> 80
            TvShowGenre.DOCUMENTARY -> 90
            TvShowGenre.DRAMA -> 18
            TvShowGenre.FAMILY -> 10751
            TvShowGenre.KIDS -> 10762
            TvShowGenre.MYSTERY -> 9648
            TvShowGenre.NEWS -> 10763
            TvShowGenre.REALITY -> 10764
            TvShowGenre.SCI_FI_AND_FANTASY -> 10765
            TvShowGenre.SOAP -> 10766
            TvShowGenre.TALK -> 10767
            TvShowGenre.WAR_AND_POLITICS -> 10768
            TvShowGenre.WESTERN -> 37
        }
    }
}