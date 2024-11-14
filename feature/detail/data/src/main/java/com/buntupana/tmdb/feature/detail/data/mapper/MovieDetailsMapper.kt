package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.data.mapper.toModel
import com.buntupana.tmdb.feature.detail.data.raw.MovieDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.Credits
import com.buntupana.tmdb.feature.detail.domain.model.MovieDetails
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun MovieDetailsRaw.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String,
    baseUrlProfile: String
): MovieDetails {

    val releaseLocalDate = try {
        LocalDate.parse(releaseDate)
    } catch (exc: DateTimeParseException) {
        null
    }

    val posterUrl = posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    val videoList = videos?.toModel().orEmpty()

    return MovieDetails(
        id = id,
        title = title,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        trailerUrl = getVideoTrailerUrl(videoList),
        overview = overview,
        tagLine = tagline,
        releaseDate = releaseLocalDate,
        userScore = (voteAverage * 10).toInt(),
        runTime = runtime,
        genreList = genres.map { it.name },
        productionCountryCodeList = productionCountries.map { it.iso_3166_1 },
        releaseDateList = releaseDates?.results?.map { it.toModel() }.orEmpty(),
        videoList = videoList,
        credits = credits?.toModel(baseUrlProfile = baseUrlProfile) ?: Credits(emptyList(), emptyList()),
        recommendationList = recommendations.results.toModel(
            baseUrlPoster = baseUrlPoster,
            baseUrlBackdrop = baseUrlBackdrop
        )
    )
}