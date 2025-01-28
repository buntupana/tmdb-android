package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.mapper.getGender
import com.buntupana.tmdb.core.data.mapper.toModel
import com.buntupana.tmdb.feature.detail.data.raw.TvShowDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.CreditsTvShow
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.domain.model.TvShowDetails
import com.panabuntu.tmdb.core.common.util.Const.RATABLE_DAYS
import com.panabuntu.tmdb.core.common.util.getLanguageName
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun TvShowDetailsRaw.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String,
    baseUrlProfile: String,
    baseUrlImdb: String,
    baseUrlFacebook: String,
    baseUrlInstagram: String,
    baseUrlX: String,
    baseUrlTiktok: String
): TvShowDetails {

    val releaseLocalDate = try {
        LocalDate.parse(firstAirDate)
    } catch (exc: DateTimeParseException) {
        null
    }

    val posterUrl = posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    val videoList = videos?.toModel().orEmpty()

    val isRatable = when {
        releaseLocalDate == null -> false
        releaseLocalDate.isBefore(LocalDate.now()) -> true
        Duration.between(releaseLocalDate.atStartOfDay(), LocalDate.now().atStartOfDay())
            .toDays() < RATABLE_DAYS -> true

        else -> false
    }

    return TvShowDetails(
        id = id,
        title = name.orEmpty(),
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        trailerUrl = getVideoTrailerUrl(videoList),
        overview = overview.orEmpty(),
        tagLine = tagline.orEmpty(),
        releaseDate = releaseLocalDate,
        userScore = if ((voteCount ?: 0) == 0) null else ((voteAverage ?: 0.0) * 10).toInt(),
        voteCount = voteCount ?: 0,
        runTime = episodeRunTime?.firstOrNull() ?: 0,
        genreList = genres?.map { it.name }.orEmpty(),
        creatorList = createdBy?.map {
            val profileUrl =
                it.profilePath.ifNotNullOrBlank { baseUrlProfile + it.profilePath }
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
        credits = credits?.toModel(baseUrlProfile = baseUrlProfile) ?: CreditsTvShow(
            emptyList(),
            emptyList()
        ),
        seasonList = seasons?.toModel(baseUrlPoster = baseUrlPoster).orEmpty(),
        lastEpisode = lastEpisodeToAir?.toModel(baseUrlBackdrop = baseUrlBackdrop),
        nextEpisode = nextEpisodeToAir?.toModel(baseUrlBackdrop = baseUrlBackdrop),
        isInAir = nextEpisodeToAir != null,
        recommendationList = recommendations.results.toModel(
            baseUrlPoster = baseUrlPoster,
            baseUrlBackdrop = baseUrlBackdrop
        ),
        isFavorite = accountStates?.favorite ?: false,
        isWatchlisted = accountStates?.watchlist ?: false,
        userRating = (accountStates?.rated?.value?.times(10))?.toInt(),
        isRateable = isRatable,
        status = status,
        originalLanguage = getLanguageName(originalLanguageCode),
        type = type,
        externalLinkList = externalLinks?.toModel(
            homepage = homepage,
            baseUrlFacebook = baseUrlFacebook,
            baseUrlInstagram = baseUrlInstagram,
            baseUrlX = baseUrlX,
            baseUrlTiktok = baseUrlTiktok,
            baseUrlImdb = baseUrlImdb
        ).orEmpty()
    )
}