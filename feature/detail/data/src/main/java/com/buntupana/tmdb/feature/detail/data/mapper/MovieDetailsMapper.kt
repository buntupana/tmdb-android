package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.database.entity.MovieDetailsEntity
import com.buntupana.tmdb.core.data.mapper.toModel
import com.buntupana.tmdb.feature.detail.data.raw.CreditsMovieRaw
import com.buntupana.tmdb.feature.detail.data.raw.ExternalLinksRaw
import com.buntupana.tmdb.feature.detail.data.raw.Genre
import com.buntupana.tmdb.feature.detail.data.raw.MediaVideosRaw
import com.buntupana.tmdb.feature.detail.data.raw.MovieDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.ProductionCountry
import com.buntupana.tmdb.feature.detail.data.raw.RecommendationsRaw
import com.buntupana.tmdb.feature.detail.data.raw.ReleaseDatesRaw
import com.buntupana.tmdb.feature.detail.domain.model.Credits
import com.buntupana.tmdb.feature.detail.domain.model.MovieDetails
import com.panabuntu.tmdb.core.common.util.Const.RATABLE_DAYS
import com.panabuntu.tmdb.core.common.util.encodeToStringSafe
import com.panabuntu.tmdb.core.common.util.getLanguageName
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun MovieDetailsRaw.toEntity(): MovieDetailsEntity {

    return MovieDetailsEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        backdropPath = backdropPath,
        overview = overview,
        tagline = tagline,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        runtime = runtime,
        genreList = Json.encodeToStringSafe(genres),
        productionCompanyList = Json.encodeToStringSafe(productionCountries),
        productionCountryList = Json.encodeToString(productionCountries),
        videos = Json.encodeToStringSafe(videos),
        credits = Json.encodeToStringSafe(credits),
        recommendations = Json.encodeToStringSafe(recommendations),
        externalLinks = Json.encodeToStringSafe(externalLinks),
        homepage = homepage,
        budget = budget,
        revenue = revenue,
        status = status,
        originalLanguageCode = originalLanguageCode,
        releaseDates = Json.encodeToStringSafe(releaseDates),
        belongsToCollection = Json.encodeToStringSafe(belongsToCollection),
        adult = adult,
        imdbId = imdbId,
        originalTitle = originalTitle,
        popularity = popularity,
        video = video,
        spokenLanguageList = Json.encodeToStringSafe(spokenLanguages),
        isFavorite = accountStates?.favorite ?: false,
        isWatchListed = accountStates?.watchlist ?: false,
        userRating = (accountStates?.rated?.value?.times(10))?.toInt()
    )
}

fun MovieDetailsEntity.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String,
    baseUrlProfile: String,
    baseUrlImdb: String,
    baseUrlFacebook: String,
    baseUrlInstagram: String,
    baseUrlX: String,
    baseUrlTiktok: String
): MovieDetails {

    val releaseLocalDate = try {
        LocalDate.parse(releaseDate)
    } catch (exc: DateTimeParseException) {
        null
    }

    val posterUrl = posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    val videoList = videos?.let { Json.decodeFromString<MediaVideosRaw>(it).toModel() }.orEmpty()

    val genreList = genreList?.let { Json.decodeFromString<List<Genre>>(it) }.orEmpty()

    val productionCountryCodeList = productionCountryList?.let {
        Json.decodeFromString<List<ProductionCountry>>(it)
    }.orEmpty()

    val releaseDateList = releaseDates?.let { Json.decodeFromString<ReleaseDatesRaw>(it) }

    val credits = credits?.let { Json.decodeFromString<CreditsMovieRaw>(it) }

    val recommendations = recommendations?.let { Json.decodeFromString<RecommendationsRaw>(it) }

    val externalLinks = externalLinks?.let { Json.decodeFromString<ExternalLinksRaw>(it) }

    val isRatable = when {
        releaseLocalDate == null -> false
        Duration.between(LocalDate.now().atStartOfDay(), releaseLocalDate.atStartOfDay())
            .toDays() < RATABLE_DAYS -> true

        else -> false
    }

    return MovieDetails(
        id = id,
        title = title,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        trailerUrl = getVideoTrailerUrl(videoList),
        overview = overview,
        tagLine = tagline,
        releaseDate = releaseLocalDate,
        userScore = if ((voteCount ?: 0) == 0) null else (voteAverage * 10).toInt(),
        voteCount = voteCount ?: 0,
        runTime = runtime,
        genreList = genreList.map { it.name },
        productionCountryCodeList = productionCountryCodeList.map { it.iso_3166_1 },
        releaseDateList = releaseDateList?.results?.map { it.toModel() }.orEmpty(),
        videoList = videoList,
        credits = credits?.toModel(baseUrlProfile = baseUrlProfile) ?: Credits(
            emptyList(),
            emptyList()
        ),
        recommendationList = recommendations?.results.orEmpty().toModel(
            baseUrlPoster = baseUrlPoster,
            baseUrlBackdrop = baseUrlBackdrop
        ),
        isFavorite = isFavorite,
        isWatchListed = isWatchListed,
        userRating = userRating,
        isRateable = isRatable,
        status = status,
        originalLanguage = getLanguageName(originalLanguageCode),
        budget = budget,
        revenue = revenue,
        externalLinkList = externalLinks?.toModel(
            homepage = homepage,
            baseUrlFacebook = baseUrlFacebook,
            baseUrlInstagram = baseUrlInstagram,
            baseUrlX = baseUrlX,
            baseUrlTiktok = baseUrlTiktok,
            baseUrlImdb = baseUrlImdb
        ).orEmpty(),
    )
}