package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.feature.detail.data.raw.TvShowDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import org.threeten.bp.LocalDate

fun TvShowDetailsRaw.toModel(): MediaDetails.TvShowDetails {

    val releaseLocalDate = LocalDate.parse(firstAirDate)

    return MediaDetails.TvShowDetails(
        id,
        name.orEmpty(),
        CoreApi.BASE_URL_POSTER + posterPath.orEmpty(),
        CoreApi.BASE_URL_BACKDROP + backdropPath.orEmpty(),
        overview.orEmpty(),
        tagline.orEmpty(),
        releaseLocalDate,
        (((voteAverage ?: 0.0) * 10)).toInt(),
        episodeRunTime?.first() ?: 0,
        genres?.map { it.name }.orEmpty()
    )
}