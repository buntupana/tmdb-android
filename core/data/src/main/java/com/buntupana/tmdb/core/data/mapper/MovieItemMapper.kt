package com.buntupana.tmdb.core.data.mapper


import com.buntupana.tmdb.core.data.database.entity.MediaSimpleEntity
import com.buntupana.tmdb.core.data.raw.MovieItemRaw
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.util.DateUtil
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun List<MovieItemRaw>.toEntity(): List<MediaSimpleEntity> {

    return map { raw ->
        MediaSimpleEntity(
            id = raw.id,
            mediaType = MediaType.MOVIE,
            title = raw.title,
            originalTitle = raw.originalTitle,
            overview = raw.overview,
            posterPath = raw.posterPath,
            backdropPath = raw.backdropPath,
            originalLanguageCode = raw.originalLanguage,
            adult = raw.adult,
            popularity = raw.popularity,
            releaseDate = raw.releaseDate,
            video = raw.video,
            voteAverage = raw.voteAverage,
            voteCount = raw.voteCount
        )
    }
}

fun MovieItemRaw.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String
): MediaItem.Movie {

    val posterUrl = posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    val releaseLocalDate = try {
        LocalDate.parse(releaseDate.orEmpty())
            .format(DateTimeFormatter.ofPattern(DateUtil.dateFormat))
    } catch (e: DateTimeParseException) {
        ""
    }

    return MediaItem.Movie(
        id = id,
        name = title,
        originalName = originalTitle,
        overview = overview.orEmpty(),
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        originalLanguageCode = originalLanguage,
        popularity = popularity ?: 0f,
        voteAverage = if ((voteCount ?: 0) == 0) null else ((voteAverage ?: 0f) * 10).toInt(),
        voteCount = voteCount ?: 0,
        releaseDate = releaseLocalDate,
        video = video ?: false,
        adult = adult
    )
}

fun List<MovieItemRaw>.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String
): List<MediaItem.Movie> {
    return map { it.toModel(baseUrlPoster = baseUrlPoster, baseUrlBackdrop = baseUrlBackdrop) }
}