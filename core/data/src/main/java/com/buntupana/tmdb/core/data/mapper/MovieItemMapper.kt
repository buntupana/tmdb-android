package com.buntupana.tmdb.core.data.mapper


import com.buntupana.tmdb.core.data.raw.MovieItemRaw
import com.panabuntu.tmdb.core.common.util.DateUtil
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun MovieItemRaw.toModel(
    baseUrlPoster : String,
    baseUrlBackdrop : String
): com.panabuntu.tmdb.core.common.model.MediaItem.Movie {

    val posterUrl = posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    val releaseLocalDate = try {
        LocalDate.parse(releaseDate.orEmpty()).format(DateTimeFormatter.ofPattern(DateUtil.dateFormat))
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