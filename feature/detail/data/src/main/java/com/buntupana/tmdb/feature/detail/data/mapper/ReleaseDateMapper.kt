package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.ReleaseDateCountry
import com.buntupana.tmdb.feature.detail.domain.model.ReleaseDateInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun ReleaseDateCountry.toModel(): ReleaseDateInfo {

    val firstRelease = releaseDates.firstOrNull()

    return ReleaseDateInfo(
        iso_3166_1,
        LocalDate.parse(firstRelease?.releaseDate, DateTimeFormatter.ISO_DATE_TIME),
        firstRelease?.certification.orEmpty()
    )
}