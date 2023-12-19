package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.raw.SeasonRaw
import com.buntupana.tmdb.feature.detail.domain.model.Season
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun SeasonRaw.toModel(): Season {
    val seasonReleaseLocalDate = try {
        LocalDate.parse(airDate)
    } catch (exc: DateTimeParseException) {
        null
    }

    return Season(
        id = id,
        name = name,
        airDate = seasonReleaseLocalDate,
        episodeCount = episodeCount,
        overview = overview,
        posterPath = posterPath,
        seasonNumber = seasonNumber,
        voteAverage = voteAverage
    )
}

fun List<SeasonRaw>.toModel(): List<Season> {
    return map { it.toModel() }
}