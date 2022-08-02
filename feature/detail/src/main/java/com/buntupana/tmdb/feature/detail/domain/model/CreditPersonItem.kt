package com.buntupana.tmdb.feature.detail.domain.model

import com.buntupana.tmdb.core.domain.entity.MediaType
import org.threeten.bp.LocalDate

data class CreditPersonItem(
    val id: Long,
    val mediaType: MediaType,
    val department: String,
    val role: String,
    val posterUrl: String,
    val backdropUrl: String,
    val popularity: Double,
    val releaseDate: LocalDate?
)
