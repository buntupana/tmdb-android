package com.panabuntu.tmdb.core.common.mapper

import com.panabuntu.tmdb.core.common.api.CoreApi
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun List<com.panabuntu.tmdb.core.common.raw.AnyMediaItemRaw>.toModel(): List<com.panabuntu.tmdb.core.common.model.MediaItem> {

    val result = mutableListOf<com.panabuntu.tmdb.core.common.model.MediaItem>()

    forEach { item ->

        val formatter = DateTimeFormatter.ofPattern(com.panabuntu.tmdb.core.common.DateUtil.dateFormat)

        val posterUrl =
            item.posterPath.ifNotNullOrBlank { CoreApi.BASE_URL_POSTER + item.posterPath.orEmpty() }
        val backdropUrl =
            item.backdropPath.ifNotNullOrBlank { CoreApi.BASE_URL_BACKDROP + item.backdropPath.orEmpty() }

        when (item.mediaType) {
            "tv" -> {
                val releaseDate = try {
                    LocalDate.parse(item.firstAirDate).format(formatter)
                } catch (e: DateTimeParseException) {
                    ""
                }

                result.add(
                    com.panabuntu.tmdb.core.common.model.MediaItem.TvShow(
                        id = item.id,
                        name = item.name.orEmpty(),
                        originalName = item.originalName.orEmpty(),
                        overview = item.overview.orEmpty(),
                        posterUrl = posterUrl,
                        backdropUrl = backdropUrl,
                        originalLanguage = item.originalLanguage,
                        genreIds = item.genreIds,
                        popularity = item.popularity ?: 0f,
                        voteAverage = ((item.voteAverage ?: 0f) * 10).toInt(),
                        voteCount = item.voteCount ?: 0,
                        releaseDate = releaseDate,
                        originCountry = item.originCountry.orEmpty()
                    )
                )
            }

            "movie" -> {
                val releaseLocalDate = try {
                    LocalDate.parse(item.releaseDate).format(formatter)
                } catch (e: DateTimeParseException) {
                    ""
                }

                result.add(
                    com.panabuntu.tmdb.core.common.model.MediaItem.Movie(
                        id = item.id,
                        name = item.title.orEmpty(),
                        originalName = item.originalTitle.orEmpty(),
                        overview = item.overview.orEmpty(),
                        posterUrl = posterUrl,
                        backdropUrl = backdropUrl,
                        originalLanguage = item.originalLanguage,
                        genreIds = item.genreIds,
                        popularity = item.popularity ?: 0f,
                        voteAverage = ((item.voteAverage ?: 0f) * 10).toInt(),
                        voteCount = item.voteCount ?: 0,
                        releaseDate = releaseLocalDate,
                        video = item.video ?: false,
                        adult = item.adult ?: false
                    )
                )
            }
            else -> {}
        }
    }
    return result
}