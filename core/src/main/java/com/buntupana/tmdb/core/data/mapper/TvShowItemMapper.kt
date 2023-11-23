package com.buntupana.tmdb.core.data.mapper

import com.buntupana.tmdb.core.data.DateUtil
import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.data.raw.TvShowRaw
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.util.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun TvShowRaw.toModel(): MediaItem.TvShow {

    val posterUrl = posterPath.ifNotNullOrBlank { CoreApi.BASE_URL_POSTER + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { CoreApi.BASE_URL_BACKDROP + backdropPath.orEmpty() }

    val releaseDate = try {
        val formatter = DateTimeFormatter.ofPattern(DateUtil.dateFormat)
        LocalDate.parse(firstAirDate.orEmpty()).format(formatter)
    } catch (e: DateTimeParseException) {
        ""
    }

    return MediaItem.TvShow(
        id = id,
        name = name,
        originalName = originalName,
        overview = overview,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        originalLanguage = originalLanguage,
        genreIds = genreIds,
        popularity = popularity,
        voteAverage = (voteAverage * 10).toInt(),
        voteCount = voteCount,
        releaseDate = releaseDate,
        originCountry = originCountry
    )
}