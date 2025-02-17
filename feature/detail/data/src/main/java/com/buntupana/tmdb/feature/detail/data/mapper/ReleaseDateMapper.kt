package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.ReleaseDateCountry
import com.buntupana.tmdb.feature.detail.domain.model.ReleaseDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun ReleaseDateCountry.toModel(): ReleaseDate {

    val firstRelease = releaseDates.firstOrNull()

    return ReleaseDate(
        iso_3166_1,
        LocalDate.parse(firstRelease?.releaseDate, DateTimeFormatter.ISO_DATE_TIME),
        firstRelease?.certification.orEmpty()
    )
}