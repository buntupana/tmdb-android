package com.buntupana.tmdb.feature.detail.domain.model

import org.threeten.bp.LocalDate

data class ReleaseDate(
    val countryCode: String,
    val releaseDate: LocalDate,
    val certification: String
)
