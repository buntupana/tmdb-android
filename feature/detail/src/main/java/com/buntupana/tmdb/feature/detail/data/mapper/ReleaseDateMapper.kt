package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.raw.ReleaseDateCountry
import com.buntupana.tmdb.feature.detail.domain.model.ReleaseDate
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

fun ReleaseDateCountry.toModel(): ReleaseDate {

    val firstRelease = releaseDates.firstOrNull()

    return ReleaseDate(
        iso_3166_1,
        LocalDate.parse(firstRelease?.releaseDate, DateTimeFormatter.ISO_DATE_TIME),
        firstRelease?.certification.orEmpty()
    )
}