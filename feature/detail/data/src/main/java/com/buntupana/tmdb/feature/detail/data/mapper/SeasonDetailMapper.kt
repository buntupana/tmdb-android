package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.database.entity.EpisodeEntity
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.SeasonDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.SeasonDetail
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import java.time.LocalDate

fun SeasonDetailsRaw.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String,
    episodeEntityList: List<EpisodeEntity>
): SeasonDetail {

    val releaseLocalDate = try {
        LocalDate.parse(airDate)
    } catch (exc: Exception) {
        null
    }

    val posterUrl = posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }

    return SeasonDetail(
        id = id,
        name = name,
        overview = overview,
        posterUrl = posterUrl,
        airDate = releaseLocalDate,
        episodes = episodeEntityList.toModel(baseUrlBackdrop = baseUrlBackdrop),
        seasonNumber = seasonNumber,
        voteAverage = voteAverage
    )
}