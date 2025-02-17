package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.database.entity.EpisodeEntity
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.EpisodeAccountStateRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.EpisodeRaw
import com.buntupana.tmdb.feature.detail.domain.model.Episode
import com.panabuntu.tmdb.core.common.util.ifNotNull
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun List<EpisodeRaw>.toEntity(
    episodeAccountStateList: List<EpisodeAccountStateRaw>?
): List<EpisodeEntity> {

    return mapIndexed { index, episodeRaw ->
        EpisodeEntity(
            id = episodeRaw.id,
            showId = episodeRaw.showId,
            seasonNumber = episodeRaw.seasonNumber,
            episodeNumber = episodeRaw.episodeNumber,
            name = episodeRaw.name,
            overview = episodeRaw.overview,
            airDate = episodeRaw.airDate,
            stillPath = episodeRaw.stillPath,
            productionCode = episodeRaw.productionCode,
            episodeType = episodeRaw.episodeType,
            voteAverage = episodeRaw.voteAverage,
            voteCount = episodeRaw.voteCount,
            runtime = episodeRaw.runtime,
            guestStarList = Json.encodeToString(episodeRaw.guestStars),
            crewList = Json.encodeToString(episodeRaw.crew),
            userRating = (episodeAccountStateList?.get(index)?.rated?.value.ifNotNull { it * 10 })?.toInt()
        )
    }
}

fun EpisodeRaw.toModel(
    baseUrlBackdrop: String,
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
        voteCount = voteCount,
        userRating = null
    )
}

fun List<EpisodeEntity>.toModel(
    baseUrlBackdrop: String,
): List<Episode> {

    return map { episodeEntity ->

        val releaseLocalDate = try {
            LocalDate.parse(episodeEntity.airDate)
        } catch (exc: DateTimeParseException) {
            null
        }

        val stillUrl =
            episodeEntity.stillPath.ifNotNullOrBlank { baseUrlBackdrop + episodeEntity.stillPath.orEmpty() }

        Episode(
            id = episodeEntity.id,
            showId = episodeEntity.showId,
            name = episodeEntity.name,
            airDate = releaseLocalDate,
            episodeNumber = episodeEntity.episodeNumber,
            overview = episodeEntity.overview,
            runtime = episodeEntity.runtime,
            seasonNumber = episodeEntity.seasonNumber,
            stillUrl = stillUrl,
            voteAverage = episodeEntity.voteAverage,
            voteCount = episodeEntity.voteCount,
            userRating = episodeEntity.userRating
        )
    }
}