package com.buntupana.tmdb.feature.detail.data.mapper

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

    return Episode(
        id,
        showId,
        name,
        releaseLocalDate,
        episodeNumber,
        overview,
        runtime,
        seasonNumber,
        stillPath,
        voteAverage,
        voteCount
    )
}