package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.data.mapper.getGender
import com.buntupana.tmdb.core.data.mapper.toModel
import com.buntupana.tmdb.core.presentation.util.ifNotNullOrBlank
import com.buntupana.tmdb.feature.detail.data.raw.TvShowDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.CreditsTvShow
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

    val posterUrl = posterPath.ifNotNullOrBlank { CoreApi.BASE_URL_POSTER + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { CoreApi.BASE_URL_BACKDROP + backdropPath.orEmpty() }

    val videoList = videos?.toModel().orEmpty()

    return TvShowDetails(
        id = id,
        title = name.orEmpty(),
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        trailerUrl = getVideoTrailerUrl(videoList),
        overview = overview.orEmpty(),
        tagLine = tagline.orEmpty(),
        releaseDate = releaseLocalDate,
        userScore = (((voteAverage ?: 0.0) * 10)).toInt(),
        runTime = episodeRunTime?.firstOrNull() ?: 0,
        genreList = genres?.map { it.name }.orEmpty(),
        creatorList = createdBy?.map {
            val profileUrl = it.profilePath.ifNotNullOrBlank{ CoreApi.BASE_URL_PROFILE + it.profilePath }
            Person.Crew.TvShow(
                id = it.id,
                name = it.name.orEmpty(),
                gender = getGender(it.gender),
                profileUrl = profileUrl,
                department = "",
                totalEpisodeCount = 0,
                jobList = emptyList()
            )
        }.orEmpty(),
        certificationList = contentRatings?.results?.map { it.toModel() }.orEmpty(),
        videoList = videoList,
        credits = credits?.toModel() ?: CreditsTvShow(emptyList(), emptyList()),
        recommendationList = recommendations.results.toModel()
    )
}