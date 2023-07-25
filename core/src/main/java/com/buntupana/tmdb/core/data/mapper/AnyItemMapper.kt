package com.buntupana.tmdb.core.data.mapper

import com.buntupana.tmdb.core.data.DateUtil
import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.data.raw.AnyMediaItemRaw
import com.buntupana.tmdb.core.domain.model.MediaItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun AnyMediaItemRaw.toModel(): MediaItem {

    val formatter = DateTimeFormatter.ofPattern(DateUtil.dateFormat)

    return when (mediaType) {
        "tv" -> {
            val releaseLocalDate = LocalDate.parse(firstAirDate)
            val releaseDate = releaseLocalDate.format(formatter)

            MediaItem.TvShow(
                id,
                name.orEmpty(),
                originalName.orEmpty(),
                overview,
                CoreApi.BASE_URL_POSTER + posterPath.orEmpty(),
                CoreApi.BASE_URL_BACKDROP + backdropPath.orEmpty(),
                originalLanguage,
                genreIds,
                popularity,
                (voteAverage * 10).toInt(),
                voteCount,
                releaseDate,
                originCountry.orEmpty()
            )
        }
        "movie" -> {

            val releaseLocalDate = try {
                LocalDate.parse(releaseDate).format(formatter)
            } catch (e: DateTimeParseException) {
                ""
            }

            MediaItem.Movie(
                id,
                title.orEmpty(),
                originalTitle.orEmpty(),
                overview,
                CoreApi.BASE_URL_POSTER + posterPath.orEmpty(),
                CoreApi.BASE_URL_BACKDROP + backdropPath.orEmpty(),
                originalLanguage,
                genreIds,
                popularity,
                (voteAverage * 10).toInt(),
                voteCount,
                releaseLocalDate,
                video ?: false,
                adult ?: false
            )
        }
        "person" -> {
            MediaItem.Person(
                id,
                name.orEmpty(),
                CoreApi.BASE_URL_PROFILE + profilePath.orEmpty(),
                popularity,
                adult ?: false,
                gender ?: 0,
                knownForDepartment.orEmpty(),
                knownFor?.map { it.title }.orEmpty()
            )
        }
        else -> {
            MediaItem.Unknown
        }
    }
}