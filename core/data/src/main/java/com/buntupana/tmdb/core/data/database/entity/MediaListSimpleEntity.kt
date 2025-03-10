package com.buntupana.tmdb.core.data.database.entity

data class MediaListSimpleEntity(
    val id: Long,
    val name: String,
    val backdropPath: String?,
    val averageRating: Float?,
    val description: String?,
    val iso31661: String,
    val iso6391: String,
    val itemCount: Int,
    val posterPath: String?,
    val isPublic: Boolean,
    val revenue: Long?,
    val runtime: String?
)