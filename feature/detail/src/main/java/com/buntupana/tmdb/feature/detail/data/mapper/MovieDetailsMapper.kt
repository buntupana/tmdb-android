package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.feature.detail.data.raw.MovieDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.Credits
import com.buntupana.tmdb.feature.detail.domain.model.MovieDetails
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeParseException

fun MovieDetailsRaw.toModel(): MovieDetails {

    val releaseLocalDate = try {
        LocalDate.parse(releaseDate)
    } catch (exc: DateTimeParseException) {
        null
    }

    val posterUrl = if (posterPath.isNullOrBlank()) "" else CoreApi.BASE_URL_POSTER + posterPath
    val backdropUrl =
        if (backdropPath.isNullOrBlank()) "" else CoreApi.BASE_URL_BACKDROP + backdropPath

    val videoList = videos?.toModel().orEmpty()

    return MovieDetails(
        id,
        title,
        posterUrl,
        backdropUrl,
        getVideoTrailerUrl(videoList),
        overview,
        tagline,
        releaseLocalDate,
        (voteAverage * 10).toInt(),
        runtime,
        genres.map { it.name },
        productionCountries.map { it.iso_3166_1 },
        releaseDates?.results?.map { it.toModel() }.orEmpty(),
        videoList,
        credits?.toModel() ?: Credits(emptyList(), emptyList())
    )
}