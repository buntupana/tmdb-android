package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.domain.model.Gender
import com.buntupana.tmdb.feature.detail.data.raw.PersonDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.PersonDetails
import java.time.LocalDate
import java.time.format.DateTimeParseException

private const val IMDB_BASE_URL = "https://www.imdb.com/name/"

fun PersonDetailsRaw.toModel(): PersonDetails {

    val birthDateLocal: LocalDate? = try {
        LocalDate.parse(birthday.orEmpty())
    } catch (exc: DateTimeParseException) {
        null
    }

    val deathDateLocal: LocalDate? = try {
        LocalDate.parse(deathDay.orEmpty())
    } catch (exc: DateTimeParseException) {
        null
    }

    val profileUrl =
        if (profilePath.isNullOrBlank()) profilePath else CoreApi.BASE_URL_PROFILE + profilePath

    val imdbLink = if (imdbId.isNullOrBlank()) imdbId else IMDB_BASE_URL + imdbId

    // Getting person age
    val age = if (deathDateLocal != null && birthDateLocal != null) {
        deathDateLocal.year - birthDateLocal.year
    } else if (birthDateLocal != null) {
        LocalDate.now().year - birthDateLocal.year
    } else {
        0
    }

    return PersonDetails(
        id,
        name,
        profileUrl.orEmpty(),
        homepage.orEmpty(),
        imdbLink.orEmpty(),
        knownForDepartment.orEmpty(),
        getGender(gender),
        birthDateLocal,
        deathDateLocal,
        age,
        placeOfBirth.orEmpty(),
        biography.orEmpty(),
        externalLinks?.toModel().orEmpty(),
        combinedCredits?.toModel().orEmpty()
    )
}

fun getGender(value: Int): Gender {
    return when (value) {
        1 -> Gender.FEMALE
        2 -> Gender.MALE
        3 -> Gender.NON_BINARY
        else -> Gender.NOT_SPECIFIED
    }
}