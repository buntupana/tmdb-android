package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.SeasonRaw
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import java.time.LocalDate

fun SeasonRaw.toModel(
    baseUrlPoster: String
): Season {
    val seasonReleaseLocalDate = try {
        LocalDate.parse(airDate)
    } catch (exc: Exception) {
        null
    }

    val posterUrl = posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }

    return Season(
        id = id,
        name = name,
        airDate = seasonReleaseLocalDate,
        episodeCount = episodeCount,
        overview = overview,
        posterUrl = posterUrl,
        seasonNumber = seasonNumber,
        voteAverage = voteAverage
    )
}

fun List<SeasonRaw>?.toModel(
    baseUrlPoster: String
): List<Season> {
    return this?.map { it.toModel(baseUrlPoster = baseUrlPoster) }.orEmpty()
}