package com.buntupana.tmdb.feature.account.domain.model

import java.time.LocalDate

data class ListItem(
    val id: Long,
    val name: String,
    val backdropUrl: String?,
    val posterUrl: String?,
    val averageRating: Int?,
    val description: String?,
    val itemCount: Int,
    val public: Boolean,
    val revenue: Int?,
    val runtime: Int?,
    val updatedAt: LocalDate?
)