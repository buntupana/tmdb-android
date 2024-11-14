package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.data.mapper.getGender
import com.buntupana.tmdb.feature.detail.data.raw.PersonDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.PersonDetails
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun PersonDetailsRaw.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String,
    baseUrlProfile: String,
    baseUrlImdb: String,
    baseUrlFacebook: String,
    baseUrlInstagram: String,
    baseUrlX: String
): PersonDetails {

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

    val profileUrl = profilePath.ifNotNullOrBlank { baseUrlProfile + profilePath }

    val imdbLink = if (imdbId.isNullOrBlank()) imdbId else baseUrlImdb + imdbId

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
        gender = getGender(gender),
        birthDate = birthDateLocal,
        deathDate = deathDateLocal,
        age = age,
        placeOfBirth = placeOfBirth.orEmpty(),
        biography = biography.orEmpty(),
        externalLinkList = externalLinks?.toModel(
            baseUrlFacebook = baseUrlFacebook,
            baseUrlInstagram = baseUrlInstagram,
            baseUrlX = baseUrlX
        ).orEmpty(),
        filmography = combinedCredits?.toModel(
            baseUrlPoster = baseUrlPoster,
            baseUrlBackdrop = baseUrlBackdrop
        ).orEmpty()
    )
}