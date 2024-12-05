package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.raw.EpisodeRaw
import com.buntupana.tmdb.feature.detail.domain.model.Episode
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun EpisodeRaw.toModel(
    baseUrlBackdrop : String,
): Episode {

    val releaseLocalDate = try {
        LocalDate.parse(airDate)
    } catch (exc: DateTimeParseException) {
        null
    }

    val stillUrl = stillPath.ifNotNullOrBlank { baseUrlBackdrop + stillPath.orEmpty() }

    return Episode(
        id = id,
        showId = showId,
        name = name,
        airDate = releaseLocalDate,
        episodeNumber = episodeNumber,
        overview = overview,
        runtime = runtime,
        seasonNumber = seasonNumber,
        stillUrl = stillUrl,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

fun List<EpisodeRaw>.toModel(
    baseUrlBackdrop : String,
): List<Episode> {
    return map { it.toModel(baseUrlBackdrop) }
}