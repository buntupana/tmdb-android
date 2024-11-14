package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.raw.SeasonDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.SeasonDetail
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun SeasonDetailsRaw.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String
): SeasonDetail {

    val releaseLocalDate = try {
        LocalDate.parse(airDate)
    } catch (exc: DateTimeParseException) {
        null
    }

    val posterUrl = posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }

    return SeasonDetail(
        id = id,
        name = name,
        overview = overview,
        posterUrl = posterUrl,
        airDate = releaseLocalDate,
        episodes = episodes.toModel(baseUrlBackdrop = baseUrlBackdrop),
        seasonNumber = seasonNumber,
        voteAverage = voteAverage
    )
}