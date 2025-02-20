package com.buntupana.tmdb.core.data.mapper

import com.buntupana.tmdb.core.data.raw.TvShowItemRaw
import com.panabuntu.tmdb.core.common.util.DateUtil
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun TvShowItemRaw.toModel(
    baseUrlPoster : String,
    baseUrlBackdrop : String
): com.panabuntu.tmdb.core.common.model.MediaItem.TvShow {

    val posterUrl = posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    val releaseDate = try {
        val formatter = DateTimeFormatter.ofPattern(DateUtil.dateFormat)
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
        voteAverage = if ((voteCount ?: 0) == 0) null else ((voteAverage ?: 0f) * 10).toInt(),
        voteCount = voteCount ?: 0,
        releaseDate = releaseDate,
        originCountry = originCountry
    )
}

fun List<TvShowItemRaw>.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String
): List<com.panabuntu.tmdb.core.common.model.MediaItem.TvShow> {
    return map { it.toModel(baseUrlPoster = baseUrlPoster, baseUrlBackdrop = baseUrlBackdrop) }
}