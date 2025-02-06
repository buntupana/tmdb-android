package com.buntupana.tmdb.feature.account.domain.model

data class ListDetail(
    val id: Int,
    val name: String,
    val description: String?,
    val itemCount: Int,
    val averageRating: Int?,
    val backdropUrl: String?,
    val isPublic: Boolean,
    val revenue: Int?,
    val runtime: Int?,
)