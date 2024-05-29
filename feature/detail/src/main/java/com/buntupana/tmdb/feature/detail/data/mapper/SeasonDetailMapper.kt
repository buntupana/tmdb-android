package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.presentation.util.ifNotNullOrBlank
import com.buntupana.tmdb.feature.detail.data.raw.SeasonDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.SeasonDetail
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun SeasonDetailsRaw.toModel(): SeasonDetail {

    val releaseLocalDate = try {
        LocalDate.parse(airDate)
    } catch (exc: DateTimeParseException) {
        null
    }

    val posterUrl = posterPath.ifNotNullOrBlank { CoreApi.BASE_URL_POSTER + posterPath.orEmpty() }

    return SeasonDetail(
        id = id,
        name = name,
        overview = overview,
        posterUrl = posterUrl,
        airDate = releaseLocalDate,
        episodes = episodes.toModel(),
        seasonNumber = seasonNumber,
        voteAverage = voteAverage
    )
}