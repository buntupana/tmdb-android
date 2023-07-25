package com.buntupana.tmdb.core.data.mapper

import com.buntupana.tmdb.core.data.DateUtil
import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.data.raw.TvShowRaw
import com.buntupana.tmdb.core.domain.model.MediaItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun TvShowRaw.toModel(): MediaItem.TvShow {

    val releaseDate = try {
        val formatter = DateTimeFormatter.ofPattern(DateUtil.dateFormat)
        LocalDate.parse(firstAirDate.orEmpty()).format(formatter)
    } catch (e: DateTimeParseException) {
        ""
    }

    return MediaItem.TvShow(
        id,
        name,
        originalName,
        overview,
        CoreApi.BASE_URL_POSTER + posterPath.orEmpty(),
        CoreApi.BASE_URL_POSTER + backdropPath.orEmpty(),
        originalLanguage,
        genreIds,
        popularity,
        (voteAverage * 10).toInt(),
        voteCount,
        releaseDate,
        originCountry
    )
}