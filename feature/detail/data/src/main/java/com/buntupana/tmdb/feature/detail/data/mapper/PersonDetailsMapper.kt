package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.raw.PersonDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.PersonDetails
import com.panabuntu.tmdb.core.common.api.CoreApi
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank
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

    val profileUrl = profilePath.ifNotNullOrBlank{ CoreApi.BASE_URL_PROFILE + profilePath }

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
        id = id,
        name = name,
        profileUrl = profileUrl.orEmpty(),
        homePageUrl = homepage.orEmpty(),
        imdbLink = imdbLink.orEmpty(),
        knownForDepartment = knownForDepartment.orEmpty(),
        gender = com.panabuntu.tmdb.core.common.mapper.getGender(gender),
        birthDate = birthDateLocal,
        deathDate = deathDateLocal,
        age = age,
        placeOfBirth = placeOfBirth.orEmpty(),
        biography = biography.orEmpty(),
        externalLinkList = externalLinks?.toModel().orEmpty(),
        filmography = combinedCredits?.toModel().orEmpty()
    )
}