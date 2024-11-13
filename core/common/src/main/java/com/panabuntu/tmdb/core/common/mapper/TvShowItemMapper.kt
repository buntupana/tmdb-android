package com.panabuntu.tmdb.core.common.mapper

import com.panabuntu.tmdb.core.common.api.CoreApi
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun com.panabuntu.tmdb.core.common.raw.TvShowRaw.toModel(): com.panabuntu.tmdb.core.common.model.MediaItem.TvShow {

    val posterUrl = posterPath.ifNotNullOrBlank { CoreApi.BASE_URL_POSTER + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { CoreApi.BASE_URL_BACKDROP + backdropPath.orEmpty() }

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