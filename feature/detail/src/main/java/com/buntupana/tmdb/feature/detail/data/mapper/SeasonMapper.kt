package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.presentation.util.ifNotNullOrBlank
import com.buntupana.tmdb.feature.detail.data.raw.SeasonRaw
import com.buntupana.tmdb.feature.detail.domain.model.Season
import java.time.LocalDate

fun SeasonRaw.toModel(): Season {
    val seasonReleaseLocalDate = try {
        LocalDate.parse(airDate)
    } catch (exc: Exception) {
        null
    }

    val posterUrl = posterPath.ifNotNullOrBlank { CoreApi.BASE_URL_POSTER + posterPath.orEmpty() }

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

fun List<SeasonRaw>.toModel(): List<Season> {
    return map { it.toModel() }
}