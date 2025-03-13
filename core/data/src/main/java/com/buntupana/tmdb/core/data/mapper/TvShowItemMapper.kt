package com.buntupana.tmdb.core.data.mapper

import com.buntupana.tmdb.core.data.database.entity.MediaSimpleEntity
import com.buntupana.tmdb.core.data.raw.TvShowItemRaw
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.util.DateUtil
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun List<TvShowItemRaw>.toEntity(): List<MediaSimpleEntity> {

    return map { raw ->
        MediaSimpleEntity(
            id = raw.id,
            mediaType = MediaType.TV_SHOW,
            title = raw.name,
            originalTitle = raw.originalName,
            posterPath = raw.posterPath,
            backdropPath = raw.backdropPath,
            overview = raw.overview,
            originalLanguageCode = raw.originalLanguage,
            popularity = raw.popularity,
            releaseDate = raw.firstAirDate,
            voteAverage = raw.voteAverage,
            voteCount = raw.voteCount,
        )
    }
}

fun TvShowItemRaw.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String
): MediaItem.TvShow {

    val posterUrl = posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    val releaseDate = try {
        val formatter = DateTimeFormatter.ofPattern(DateUtil.dateFormat)
        LocalDate.parse(firstAirDate.orEmpty()).format(formatter)
    } catch (e: DateTimeParseException) {
        ""
    }

    return MediaItem.TvShow(
        id = id,
        name = name,
        originalName = originalName,
        overview = overview.orEmpty(),
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        originalLanguageCode = originalLanguage,
        popularity = popularity ?: 0f,
        voteAverage = if ((voteCount ?: 0) == 0) null else ((voteAverage ?: 0f) * 10).toInt(),
        voteCount = voteCount ?: 0,
        releaseDate = releaseDate
    )
}

fun List<TvShowItemRaw>.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String
): List<MediaItem.TvShow> {
    return map { it.toModel(baseUrlPoster = baseUrlPoster, baseUrlBackdrop = baseUrlBackdrop) }
}