package com.buntupana.tmdb.core.data.mapper

import com.buntupana.tmdb.core.data.DateUtil
import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.data.raw.MovieItemRaw
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.util.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun MovieItemRaw.toModel(): MediaItem.Movie {

    val posterUrl = posterPath.ifNotNullOrBlank { CoreApi.BASE_URL_POSTER + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { CoreApi.BASE_URL_BACKDROP + backdropPath.orEmpty() }

    val releaseLocalDate = try {
        LocalDate.parse(releaseDate.orEmpty()).format(DateTimeFormatter.ofPattern(DateUtil.dateFormat))
    } catch (e: DateTimeParseException) {
        ""
    }

    return MediaItem.Movie(
        id = id,
        name = title,
        originalName = originalTitle,
        overview = overview,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        originalLanguage = originalLanguage,
        genreIds = genreIds,
        popularity = popularity,
        voteAverage = (voteAverage * 10).toInt(),
        voteCount = voteCount,
        releaseDate = releaseLocalDate,
        video = video,
        adult = adult
    )
}