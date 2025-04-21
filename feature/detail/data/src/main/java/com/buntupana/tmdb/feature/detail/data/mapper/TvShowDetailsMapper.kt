package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.database.entity.MediaEntity
import com.buntupana.tmdb.core.data.mapper.getGender
import com.buntupana.tmdb.core.data.mapper.toModel
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.ContentRatingsRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.CountryWatchProvider
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.CreatedBy
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.CreditsTvShowRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.EpisodeRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.ExternalLinksRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.Genre
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.MediaVideosRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.ProviderInfo
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.RecommendationsRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.SeasonRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.TvShowDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.CreditsTvShow
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.domain.model.Providers
import com.buntupana.tmdb.feature.detail.domain.model.TvShowDetails
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.util.Const.RATABLE_DAYS
import com.panabuntu.tmdb.core.common.util.encodeToStringSafe
import com.panabuntu.tmdb.core.common.util.getLanguageName
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import com.panabuntu.tmdb.core.common.util.isNotNullOrEmpty
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun TvShowDetailsRaw.toEntity(
    countryCode: String
): MediaEntity {

    val credits = credits?.copy(cast = credits.cast.take(9), crew = credits.crew.take(9))

    val countryWatchProvider = watchProviders?.results?.get(countryCode)

    return MediaEntity(
        id = id,
        mediaType = MediaType.TV_SHOW,
        title = name.orEmpty(),
        posterPath = posterPath,
        backdropPath = backdropPath,
        overview = overview,
        voteCount = voteCount ?: 0,
        genreList = Json.encodeToStringSafe(genres),
        credits = Json.encodeToStringSafe(credits),
        seasonList = Json.encodeToStringSafe(seasons),
        isFavorite = accountStates?.favorite ?: false,
        isWatchListed = accountStates?.watchlist ?: false,
        userRating = (accountStates?.rated?.value?.times(10))?.toInt(),
        status = status,
        type = type,
        tagline = tagline,
        nextEpisodeToAir = Json.encodeToStringSafe(nextEpisodeToAir),
        networkList = Json.encodeToString(networks),
        lastEpisodeToAir = Json.encodeToStringSafe(lastEpisodeToAir),
        lastAirDate = lastAirDate,
        languageList = Json.encodeToStringSafe(languages),
        inProduction = inProduction,
        homepage = homepage,
        episodeRunTimeList = Json.encodeToString(episodeRunTime),
        createdByList = Json.encodeToString(createdBy),
        adult = adult,
        releaseDate = firstAirDate,
        voteAverage = voteAverage,
        spokenLanguageList = Json.encodeToString(spokenLanguages),
        productionCountryList = Json.encodeToString(productionCountries),
        productionCompanyList = Json.encodeToString(productionCompanies),
        popularity = popularity,
        originalTitle = originalName,
        originalLanguageCode = originalLanguageCode,
        originCountryList = Json.encodeToString(originCountry),
        numberOfSeasons = numberOfSeasons,
        numberOfEpisodes = numberOfEpisodes,
        videos = Json.encodeToStringSafe(videos),
        contentRatings = Json.encodeToStringSafe(contentRatings),
        recommendations = Json.encodeToStringSafe(recommendations),
        externalLinks = Json.encodeToStringSafe(externalLinks),
        watchProviders = Json.encodeToStringSafe(countryWatchProvider)
    )
}

fun MediaEntity.toTvShowModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String,
    baseUrlProfile: String,
    baseUrlImdb: String,
    baseUrlFacebook: String,
    baseUrlInstagram: String,
    baseUrlX: String,
    baseUrlTiktok: String,
    baseUrlProvider: String
): TvShowDetails {

    val releaseLocalDate = try {
        LocalDate.parse(releaseDate)
    } catch (exc: DateTimeParseException) {
        null
    }

    val posterUrl = posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    val videoList = videos?.let { Json.decodeFromString<MediaVideosRaw>(it) }?.toModel().orEmpty()

    val runTime =
        episodeRunTimeList?.let { Json.decodeFromString<List<Long>>(it) }?.firstOrNull() ?: 0L

    val genderList = genreList?.let { Json.decodeFromString<List<Genre>>(it) }

    val createdBy = createdByList?.let { Json.decodeFromString<List<CreatedBy>>(it) }

    val contentRatings = contentRatings?.let { Json.decodeFromString<ContentRatingsRaw>(it) }

    val credits = credits?.let { Json.decodeFromString<CreditsTvShowRaw>(it) }

    val seasons = seasonList?.let { Json.decodeFromString<List<SeasonRaw>>(it) }

    val lastEpisodeToAir = lastEpisodeToAir?.let { Json.decodeFromString<EpisodeRaw>(it) }

    val nextEpisodeToAir = nextEpisodeToAir?.let {
        Json.decodeFromString<EpisodeRaw>(it)
    }

    val originCountryList = originCountryList?.let { Json.decodeFromString<List<String>>(it) }

    val recommendations = recommendations?.let { Json.decodeFromString<RecommendationsRaw>(it) }

    val externalLinks = externalLinks?.let { Json.decodeFromString<ExternalLinksRaw>(it) }

    val isRatable = when {
        releaseLocalDate == null -> false
        releaseLocalDate.isBefore(LocalDate.now()) -> true
        Duration.between(releaseLocalDate.atStartOfDay(), LocalDate.now().atStartOfDay())
            .toDays() < RATABLE_DAYS -> true

        else -> false
    }

    val watchProvider = watchProviders?.let { Json.decodeFromString<CountryWatchProvider>(it) }

    val providerInfoList = mutableListOf<ProviderInfo>()

    if (watchProvider?.flatrate.isNotNullOrEmpty()) {
        providerInfoList.addAll(watchProvider?.flatrate.orEmpty())
    }
    if (watchProvider?.buy.isNotNullOrEmpty()) {
        providerInfoList.addAll(watchProvider?.buy.orEmpty())
    }
    if (watchProvider?.rent.isNotNullOrEmpty()) {
        providerInfoList.addAll(watchProvider?.rent.orEmpty())
    }
    if (watchProvider?.ads.isNotNullOrEmpty()) {
        providerInfoList.addAll(watchProvider?.ads.orEmpty())
    }

    val providers = Providers(
        justWatchLink = watchProvider?.link.orEmpty(),
        logoUrlList = providerInfoList.map {
            baseUrlProvider + it.logoPath
        }.distinct()
    )

    return TvShowDetails(
        id = id,
        title = title,
        originalTitle = originalTitle,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        trailerUrl = getVideoTrailerUrl(videoList),
        overview = overview.orEmpty(),
        tagLine = tagline.orEmpty(),
        releaseDate = releaseLocalDate,
        userScore = if ((voteCount ?: 0) == 0) null else ((voteAverage ?: 0.0f) * 10).toInt(),
        voteCount = voteCount ?: 0,
        runTime = runTime,
        genreList = genderList?.map { it.name }.orEmpty(),
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
        originCountryList = originCountryList.orEmpty(),
        recommendationList = recommendations?.results?.toModel(
            baseUrlPoster = baseUrlPoster,
            baseUrlBackdrop = baseUrlBackdrop
        ).orEmpty(),
        isFavorite = isFavorite,
        isWatchlisted = isWatchListed,
        userRating = userRating,
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
        ).orEmpty(),
        providers = providers
    )
}