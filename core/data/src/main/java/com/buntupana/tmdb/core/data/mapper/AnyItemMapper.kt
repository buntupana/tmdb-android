package com.buntupana.tmdb.core.data.mapper

import com.buntupana.tmdb.core.data.database.entity.AnyMediaEntity
import com.buntupana.tmdb.core.data.raw.AnyMediaItemRaw
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.util.DateUtil
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun List<AnyMediaItemRaw>.toEntity(mediaType: MediaType): List<AnyMediaEntity> {

    return map {
        AnyMediaEntity(
            id = it.id,
            name = it.name.orEmpty(),
            originalName = it.originalName.orEmpty(),
            overview = it.overview.orEmpty(),
            posterPath = it.posterPath.orEmpty(),
            backdropPath = it.backdropPath.orEmpty(),
            originalLanguage = it.originalLanguage,
            voteAverage = it.voteAverage,
            popularity = it.popularity,
            genreIdList = Json.encodeToString(it.genreIds),
            mediaType = mediaType.value,
            voteCount = it.voteCount,
            title = it.title,
            originalTitle = it.originalTitle,
            gender = it.gender,
            profilePath = it.profilePath,
            originCountryList =Json.encodeToString( it.originCountry),
            firstAirDate = it.firstAirDate,
            releaseDate = it.releaseDate,
            video = it.video,
            adult = it.adult,
            knownForList = Json.encodeToString(it.knownFor),
            knownForDepartment = it.knownForDepartment
        )
    }
}

fun AnyMediaEntity.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String
): MediaItem? {
    val formatter = DateTimeFormatter.ofPattern(DateUtil.dateFormat)

    val posterUrl =
        posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    val genreList = genreIdList?.let { Json.decodeFromString<List<Int>>(it) }
    val originCountryList = originCountryList?.let { Json.decodeFromString<List<String>>(it) }

    return when (mediaType) {
        "tv" -> {
            val releaseDate = try {
                LocalDate.parse(firstAirDate).format(formatter)
            } catch (e: DateTimeParseException) {
                ""
            }

            MediaItem.TvShow(
                id = id,
                name = name.orEmpty(),
                originalName = originalName.orEmpty(),
                overview = overview.orEmpty(),
                posterUrl = posterUrl,
                backdropUrl = backdropUrl,
                originalLanguage = originalLanguage,
                popularity = popularity ?: 0f,
                voteAverage = if ((voteCount ?: 0) == 0) null else ((voteAverage
                    ?: 0f) * 10).toInt(),
                voteCount = voteCount ?: 0,
                releaseDate = releaseDate,
                originCountryList = originCountryList.orEmpty()
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
                overview = overview.orEmpty(),
                posterUrl = posterUrl,
                backdropUrl = backdropUrl,
                originalLanguage = originalLanguage,
                popularity = popularity ?: 0f,
                voteAverage = if ((voteCount ?: 0) == 0) null else ((voteAverage
                    ?: 0f) * 10).toInt(),
                voteCount = voteCount ?: 0,
                releaseDate = releaseLocalDate,
                video = video ?: false,
                adult = adult ?: false
            )
        }

        else -> null
    }
}

@JvmName("fromEntityMethod")
fun List<AnyMediaEntity>.toModel(
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

        val genreList = item.genreIdList?.let { Json.decodeFromString<List<Int>>(it) }
        val originCountryList = item.originCountryList?.let { Json.decodeFromString<List<String>>(it) }

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
                        popularity = item.popularity ?: 0f,
                        voteAverage = if ((item.voteCount
                                ?: 0) == 0
                        ) null else ((item.voteAverage
                            ?: 0f) * 10).toInt(),
                        voteCount = item.voteCount ?: 0,
                        releaseDate = releaseDate,
                        originCountryList = originCountryList.orEmpty()
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

@JvmName("fromRawMethod")
fun List<AnyMediaItemRaw>.toModel(
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
                        popularity = item.popularity ?: 0f,
                        voteAverage = if ((item.voteCount
                                ?: 0) == 0
                        ) null else ((item.voteAverage
                            ?: 0f) * 10).toInt(),
                        voteCount = item.voteCount ?: 0,
                        releaseDate = releaseDate,
                        originCountryList = item.originCountry.orEmpty()
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