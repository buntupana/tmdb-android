package com.buntupana.tmdb.data.mapper

import com.buntupana.tmdb.data.raw.TvShowRaw
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun TvShowRaw.toModel(
    baseUrlPoster : String,
    baseUrlBackdrop : String
): com.panabuntu.tmdb.core.common.model.MediaItem.TvShow {

    val posterUrl = posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    val releaseDate = try {
        val formatter = DateTimeFormatter.ofPattern(com.panabuntu.tmdb.core.common.DateUtil.dateFormat)
        LocalDate.parse(firstAirDate.orEmpty()).format(formatter)
    } catch (e: DateTimeParseException) {
        ""
    }

    return com.panabuntu.tmdb.core.common.model.MediaItem.TvShow(
        id = id,
        name = name,
        originalName = originalName,
        overview = overview.orEmpty(),
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        originalLanguage = originalLanguage,
        genreIds = genreIds,
        popularity = popularity ?: 0f,
        voteAverage = ((voteAverage ?: 0f) * 10).toInt(),
        voteCount = voteCount ?: 0,
        releaseDate = releaseDate,
        originCountry = originCountry
    )
}