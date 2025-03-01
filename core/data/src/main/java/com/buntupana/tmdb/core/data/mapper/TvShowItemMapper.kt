package com.buntupana.tmdb.core.data.mapper

import com.buntupana.tmdb.core.data.database.entity.TvShowSimpleEntity
import com.buntupana.tmdb.core.data.raw.TvShowItemRaw
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.util.DateUtil
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun List<TvShowItemRaw>.toEntity(): List<TvShowSimpleEntity> {

    return map { raw ->
        TvShowSimpleEntity(
            id = raw.id,
            name = raw.name,
            originalName = raw.originalName,
            posterPath = raw.posterPath,
            backdropPath = raw.backdropPath,
            overview = raw.overview,
            originalLanguageCode = raw.originalLanguage,
            originCountryList = Json.encodeToString(raw.originCountry),
            popularity = raw.popularity,
            firstAirDate = raw.firstAirDate,
            voteAverage = raw.voteAverage,
            voteCount = raw.voteCount
        )
    }
}

fun TvShowSimpleEntity.toModel(
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

    val originCountryList = originCountryList?.let { Json.decodeFromString<List<String>>(it) }

    return MediaItem.TvShow(
        id = id,
        name = name,
        originalName = originalName,
        overview = overview.orEmpty(),
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        originalLanguage = originalLanguageCode,
        popularity = popularity ?: 0f,
        voteAverage = if ((voteCount ?: 0) == 0) null else ((voteAverage ?: 0f) * 10).toInt(),
        voteCount = voteCount ?: 0,
        releaseDate = releaseDate,
        originCountryList = originCountryList ?: emptyList()
    )
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
        originalLanguage = originalLanguage,
        popularity = popularity ?: 0f,
        voteAverage = if ((voteCount ?: 0) == 0) null else ((voteAverage ?: 0f) * 10).toInt(),
        voteCount = voteCount ?: 0,
        releaseDate = releaseDate,
        originCountryList = originCountry
    )
}

fun List<TvShowItemRaw>.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String
): List<MediaItem.TvShow> {
    return map { it.toModel(baseUrlPoster = baseUrlPoster, baseUrlBackdrop = baseUrlBackdrop) }
}