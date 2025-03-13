package com.buntupana.tmdb.core.data.mapper

import com.buntupana.tmdb.core.data.database.entity.MediaEntity
import com.buntupana.tmdb.core.data.database.entity.MediaSimpleEntity
import com.buntupana.tmdb.core.data.raw.MediaItemRaw
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.util.DateUtil
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun List<MediaItemRaw>.toEntity(): List<MediaSimpleEntity> {
    return flatMap {

        val mediaType = when (it.mediaType) {
            MediaType.MOVIE.value -> MediaType.MOVIE
            MediaType.TV_SHOW.value -> MediaType.TV_SHOW
            else -> return listOf()
        }

        listOf(
            MediaSimpleEntity(
                id = it.id,
                mediaType = mediaType,
                title = it.title ?: it.name ?: "",
                originalTitle = it.originalTitle ?: it.originalName ?: "",
                adult = it.adult == true,
                backdropPath = it.backdropPath,
                originalLanguageCode = it.originalLanguage,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                releaseDate = it.releaseDate ?: it.firstAirDate
            )
        )
    }
}

fun MediaEntity.toItemModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String
): MediaItem {
    val formatter = DateTimeFormatter.ofPattern(DateUtil.dateFormat)

    val posterUrl =
        posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    val releaseDate = try {
        LocalDate.parse(releaseDate).format(formatter)
    } catch (e: Exception) {
        ""
    }

    return when (mediaType) {
        MediaType.TV_SHOW -> {

            MediaItem.TvShow(
                id = id,
                name = title,
                originalName = originalTitle,
                overview = overview.orEmpty(),
                posterUrl = posterUrl,
                backdropUrl = backdropUrl,
                originalLanguageCode = originalLanguageCode,
                popularity = popularity ?: 0f,
                voteAverage = if ((voteCount ?: 0) == 0) null else ((voteAverage
                    ?: 0f) * 10).toInt(),
                voteCount = voteCount ?: 0,
                releaseDate = releaseDate
            )
        }

        MediaType.MOVIE -> {

            MediaItem.Movie(
                id = id,
                name = title,
                originalName = originalTitle,
                overview = overview.orEmpty(),
                posterUrl = posterUrl,
                backdropUrl = backdropUrl,
                originalLanguageCode = originalLanguageCode,
                popularity = popularity ?: 0f,
                voteAverage = if ((voteCount ?: 0) == 0) null else ((voteAverage
                    ?: 0f) * 10).toInt(),
                voteCount = voteCount ?: 0,
                releaseDate = releaseDate,
                video = video ?: false,
                adult = adult
            )
        }
    }
}

fun MediaEntity.toMovieItemModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String
): MediaItem.Movie {
    val formatter = DateTimeFormatter.ofPattern(DateUtil.dateFormat)

    val posterUrl =
        posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    val releaseDate = try {
        LocalDate.parse(releaseDate).format(formatter)
    } catch (e: Exception) {
        ""
    }

    return MediaItem.Movie(
        id = id,
        name = title,
        originalName = originalTitle,
        overview = overview.orEmpty(),
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        originalLanguageCode = originalLanguageCode,
        popularity = popularity ?: 0f,
        voteAverage = if ((voteCount ?: 0) == 0) null else ((voteAverage
            ?: 0f) * 10).toInt(),
        voteCount = voteCount ?: 0,
        releaseDate = releaseDate,
        video = video ?: false,
        adult = adult
    )
}

fun MediaEntity.toTvShowItemModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String
): MediaItem.TvShow {
    val formatter = DateTimeFormatter.ofPattern(DateUtil.dateFormat)

    val posterUrl =
        posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    val releaseDate = try {
        LocalDate.parse(releaseDate).format(formatter)
    } catch (e: Exception) {
        ""
    }

    return MediaItem.TvShow(
        id = id,
        name = title,
        originalName = originalTitle,
        overview = overview.orEmpty(),
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        originalLanguageCode = originalLanguageCode,
        popularity = popularity ?: 0f,
        voteAverage = if ((voteCount ?: 0) == 0) null else ((voteAverage
            ?: 0f) * 10).toInt(),
        voteCount = voteCount ?: 0,
        releaseDate = releaseDate
    )
}

@JvmName("fromRawMethod")
fun List<MediaItemRaw>.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String
): List<MediaItem> {

    val result = mutableListOf<MediaItem>()

    forEach { item ->

        val formatter = DateTimeFormatter.ofPattern(DateUtil.dateFormat)

        val posterUrl =
            item.posterPath.ifNotNullOrBlank { baseUrlPoster + item.posterPath.orEmpty() }
        val backdropUrl =
            item.backdropPath.ifNotNullOrBlank { baseUrlBackdrop + item.backdropPath.orEmpty() }

        when (item.mediaType) {
            MediaType.TV_SHOW.value -> {
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
                        originalLanguageCode = item.originalLanguage,
                        popularity = item.popularity ?: 0f,
                        voteAverage = if ((item.voteCount
                                ?: 0) == 0
                        ) null else ((item.voteAverage
                            ?: 0f) * 10).toInt(),
                        voteCount = item.voteCount ?: 0,
                        releaseDate = releaseDate
                    )
                )
            }

            MediaType.MOVIE.value -> {
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
                        originalLanguageCode = item.originalLanguage,
                        popularity = item.popularity ?: 0f,
                        voteAverage = if ((item.voteCount
                                ?: 0) == 0
                        ) null else ((item.voteAverage
                            ?: 0f) * 10).toInt(),
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