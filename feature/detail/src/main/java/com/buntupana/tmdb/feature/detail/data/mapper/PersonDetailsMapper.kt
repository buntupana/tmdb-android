package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.domain.model.Gender
import com.buntupana.tmdb.feature.detail.data.raw.PersonDetailsRaw
import com.buntupana.tmdb.feature.detail.domain.model.PersonDetails
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeParseException

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
        if (profilePath.isBlank()) profilePath else CoreApi.BASE_URL_PROFILE + profilePath

    return PersonDetails(
        id,
        name,
        profileUrl,
        homepage.orEmpty(),
        knownForDepartment.orEmpty(),
        getGender(gender),
        birthDateLocal,
        deathDateLocal,
        placeOfBirth.orEmpty(),
        biography.orEmpty()
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