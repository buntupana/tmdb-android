package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.feature.detail.data.raw.TvShowDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.CrewPersonItem
import com.buntupana.tmdb.feature.detail.domain.model.TvShowDetails
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeParseException

fun TvShowDetailsRaw.toModel(): TvShowDetails {

    val releaseLocalDate = try {
        LocalDate.parse(firstAirDate)
    } catch (exc: DateTimeParseException) {
        null
    }

    return TvShowDetails(
        id,
        name.orEmpty(),
        CoreApi.BASE_URL_POSTER + posterPath.orEmpty(),
        CoreApi.BASE_URL_BACKDROP + backdropPath.orEmpty(),
        overview.orEmpty(),
        tagline.orEmpty(),
        releaseLocalDate,
        (((voteAverage ?: 0.0) * 10)).toInt(),
        episodeRunTime?.firstOrNull() ?: 0,
        genres?.map { it.name }.orEmpty(),
        createdBy?.map {
            CrewPersonItem(
                it.id,
                it.name.orEmpty(),
                CoreApi.BASE_URL_PROFILE + it.profilePath,
                ""
            )
        }.orEmpty()
    )
}