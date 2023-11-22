package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.feature.detail.data.raw.TvShowDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.Credits
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.domain.model.TvShowDetails
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun TvShowDetailsRaw.toModel(): TvShowDetails {

    val releaseLocalDate = try {
        LocalDate.parse(firstAirDate)
    } catch (exc: DateTimeParseException) {
        null
    }

    val posterUrl = if (posterPath.isNullOrBlank()) "" else CoreApi.BASE_URL_POSTER + posterPath
    val backdropUrl =
        if (backdropPath.isNullOrBlank()) "" else CoreApi.BASE_URL_BACKDROP + backdropPath

    val videoList = videos?.toModel().orEmpty()

    return TvShowDetails(
        id,
        name.orEmpty(),
        posterUrl,
        backdropUrl,
        getVideoTrailerUrl(videoList),
        overview.orEmpty(),
        tagline.orEmpty(),
        releaseLocalDate,
        (((voteAverage ?: 0.0) * 10)).toInt(),
        episodeRunTime?.firstOrNull() ?: 0,
        genres?.map { it.name }.orEmpty(),
        createdBy?.map {
            Person.Crew(
                id = it.id,
                name = it.name.orEmpty(),
                profileUrl = CoreApi.BASE_URL_PROFILE + it.profilePath,
                department = "",
                job = ""
            )
        }.orEmpty(),
        contentRatings?.results?.map { it.toModel() }.orEmpty(),
        videoList,
        credits?.toModel() ?: Credits(emptyList(), emptyList())
    )
}