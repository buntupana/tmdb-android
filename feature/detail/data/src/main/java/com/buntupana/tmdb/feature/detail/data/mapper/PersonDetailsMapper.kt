package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.mapper.getGender
import com.buntupana.tmdb.feature.detail.data.raw.PersonDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.PersonDetails
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun PersonDetailsRaw.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String,
    baseUrlProfile: String,
    baseUrlImdb: String,
    baseUrlFacebook: String,
    baseUrlInstagram: String,
    baseUrlX: String,
    baseUrlTiktok: String
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
        homePageUrl = homepage,
        knownForDepartment = knownForDepartment.orEmpty(),
        gender = getGender(gender),
        birthDate = birthDateLocal,
        deathDate = deathDateLocal,
        age = age,
        placeOfBirth = placeOfBirth.orEmpty(),
        biography = biography.orEmpty(),
        externalLinkList = externalLinks?.toModel(
            homepage = homepage,
            baseUrlFacebook = baseUrlFacebook,
            baseUrlInstagram = baseUrlInstagram,
            baseUrlX = baseUrlX,
            baseUrlTiktok = baseUrlTiktok,
            baseUrlImdb = baseUrlImdb
        ).orEmpty(),
        filmography = combinedCredits?.toModel(
            baseUrlPoster = baseUrlPoster,
            baseUrlBackdrop = baseUrlBackdrop
        ).orEmpty()
    )
}