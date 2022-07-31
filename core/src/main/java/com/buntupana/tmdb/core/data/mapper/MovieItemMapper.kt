package com.buntupana.tmdb.core.data.mapper

import com.buntupana.tmdb.core.data.DateUtil
import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.data.raw.MovieItemRaw
import com.buntupana.tmdb.core.domain.model.MediaItem
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException

fun MovieItemRaw.toModel(): MediaItem.Movie {

    val releaseLocalDate = try {
        LocalDate.parse(releaseDate.orEmpty()).format(DateTimeFormatter.ofPattern(DateUtil.dateFormat))
    } catch (e: DateTimeParseException) {
        ""
    }

    return MediaItem.Movie(
        id,
        title,
        originalTitle,
        overview,
        CoreApi.BASE_URL_POSTER + posterPath.orEmpty(),
        CoreApi.BASE_URL_POSTER + backdropPath.orEmpty(),
        originalLanguage,
        genreIds,
        popularity,
        (voteAverage * 10).toInt(),
        voteCount,
        releaseLocalDate,
        video,
        adult
    )
}