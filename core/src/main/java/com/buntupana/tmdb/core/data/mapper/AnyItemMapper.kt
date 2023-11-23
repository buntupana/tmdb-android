package com.buntupana.tmdb.core.data.mapper

import com.buntupana.tmdb.core.data.DateUtil
import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.data.raw.AnyMediaItemRaw
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.core.presentation.util.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun AnyMediaItemRaw.toModel(): MediaItem {

    val formatter = DateTimeFormatter.ofPattern(DateUtil.dateFormat)

    val posterUrl = posterPath.ifNotNullOrBlank { CoreApi.BASE_URL_POSTER + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { CoreApi.BASE_URL_BACKDROP + backdropPath.orEmpty() }

    return when (mediaType) {
        "tv" -> {
            val releaseLocalDate = LocalDate.parse(firstAirDate)
            val releaseDate = releaseLocalDate.format(formatter)

            MediaItem.TvShow(
                id = id,
                name = name.orEmpty(),
                originalName = originalName.orEmpty(),
                overview = overview,
                posterUrl = posterUrl,
                backdropUrl = backdropUrl,
                originalLanguage = originalLanguage,
                genreIds = genreIds,
                popularity = popularity,
                voteAverage = (voteAverage * 10).toInt(),
                voteCount = voteCount,
                releaseDate = releaseDate,
                originCountry = originCountry.orEmpty()
            )
        }

        "movie" -> {

            val releaseLocalDate = try {
                LocalDate.parse(releaseDate).format(formatter)
            } catch (e: DateTimeParseException) {
                ""
            }

            MediaItem.Movie(
                id = id,
                name = title.orEmpty(),
                originalName = originalTitle.orEmpty(),
                overview = overview,
                posterUrl = posterUrl,
                backdropUrl = backdropUrl,
                originalLanguage = originalLanguage,
                genreIds = genreIds,
                popularity = popularity,
                voteAverage = (voteAverage * 10).toInt(),
                voteCount = voteCount,
                releaseDate = releaseLocalDate,
                video = video ?: false,
                adult = adult ?: false
            )
        }

        else -> {
            MediaItem.Unknown
        }
    }
}