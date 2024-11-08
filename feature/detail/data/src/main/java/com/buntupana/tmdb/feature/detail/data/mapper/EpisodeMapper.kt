package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.presentation.util.ifNotNullOrBlank
import com.buntupana.tmdb.feature.detail.data.raw.EpisodeRaw
import com.buntupana.tmdb.feature.detail.domain.model.Episode
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun EpisodeRaw.toModel(): Episode {

    val releaseLocalDate = try {
        LocalDate.parse(airDate)
    } catch (exc: DateTimeParseException) {
        null
    }

    val stillUrl = stillPath.ifNotNullOrBlank { CoreApi.BASE_URL_BACKDROP + stillPath.orEmpty() }

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

fun List<EpisodeRaw>.toModel(): List<Episode> {
    return map { it.toModel() }
}