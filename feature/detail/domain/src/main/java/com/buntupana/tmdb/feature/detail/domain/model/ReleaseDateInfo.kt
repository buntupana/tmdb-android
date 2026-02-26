package com.buntupana.tmdb.feature.detail.domain.model

import java.time.LocalDate


data class ReleaseDateInfo(
    val countryCode: String,
    val releaseDate: LocalDate,
    val certification: String
)
