package com.buntupana.tmdb.core.data.mapper

import com.buntupana.tmdb.core.data.raw.AnyMediaItemRaw
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank
import com.panabuntu.tmdb.core.common.model.MediaItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun List<AnyMediaItemRaw>.toModel(
    baseUrlPoster : String,
    baseUrlBackdrop : String
): List<MediaItem> {

    val result = mutableListOf<MediaItem>()

    forEach { item ->

        val formatter = DateTimeFormatter.ofPattern(com.panabuntu.tmdb.core.common.DateUtil.dateFormat)

        val posterUrl =
            item.posterPath.ifNotNullOrBlank { baseUrlPoster + item.posterPath.orEmpty() }
        val backdropUrl =
            item.backdropPath.ifNotNullOrBlank { baseUrlBackdrop + item.backdropPath.orEmpty() }

        when (item.mediaType) {
            "tv" -> {
                val releaseDate = try {
                    LocalDate.parse(item.firstAirDate).format(formatter)
                } catch (e: DateTimeParseException) {
                    ""
                }

                result.add(
                    MediaItem.TvShow(
                        id = item.id,
                        name = item.name.orEmpty(),
                        originalName = item.originalName.orEmpty(),
                        overview = item.overview.orEmpty(),
                        posterUrl = posterUrl,
                        backdropUrl = backdropUrl,
                        originalLanguage = item.originalLanguage,
                        genreIds = item.genreIds,
                        popularity = item.popularity ?: 0f,
                        voteAverage = if ((item.voteCount ?: 0) == 0) null else ((item.voteAverage ?: 0f) * 10).toInt(),
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
                    MediaItem.Movie(
                        id = item.id,
                        name = item.title.orEmpty(),
                        originalName = item.originalTitle.orEmpty(),
                        overview = item.overview.orEmpty(),
                        posterUrl = posterUrl,
                        backdropUrl = backdropUrl,
                        originalLanguage = item.originalLanguage,
                        genreIds = item.genreIds,
                        popularity = item.popularity ?: 0f,
                        voteAverage = if ((item.voteCount ?: 0) == 0) null else ((item.voteAverage ?: 0f) * 10).toInt(),
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