package com.panabuntu.tmdb.core.common.mapper


import com.panabuntu.tmdb.core.common.api.CoreApi
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun com.panabuntu.tmdb.core.common.raw.MovieItemRaw.toModel(): com.panabuntu.tmdb.core.common.model.MediaItem.Movie {

    val posterUrl = posterPath.ifNotNullOrBlank { CoreApi.BASE_URL_POSTER + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { CoreApi.BASE_URL_BACKDROP + backdropPath.orEmpty() }

    val releaseLocalDate = try {
        LocalDate.parse(releaseDate.orEmpty()).format(DateTimeFormatter.ofPattern(com.panabuntu.tmdb.core.common.DateUtil.dateFormat))
    } catch (e: DateTimeParseException) {
        ""
    }

    return com.panabuntu.tmdb.core.common.model.MediaItem.Movie(
        id = id,
        name = title,
        originalName = originalTitle,
        overview = overview.orEmpty(),
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        originalLanguage = originalLanguage,
        genreIds = genreIds,
        popularity = popularity ?: 0f,
        voteAverage = ((voteAverage ?: 0f) * 10).toInt(),
        voteCount = voteCount ?: 0,
        releaseDate = releaseLocalDate,
        video = video ?: false,
        adult = adult
    )
}