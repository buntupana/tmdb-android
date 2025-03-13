package com.buntupana.tmdb.core.data.database.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_list_details")
data class UserListDetailsEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val backdropPath: String?,
    val adult: Int? = null,
    val averageRating: Float?,
    val description: String?,
    val iso31661: String,
    val iso6391: String,
    val itemCount: Int,
    val posterPath: String?,
    val isPublic: Boolean,
    val revenue: Long?,
    val runtime: String?,
    val accountObjectId: String? = null,
    val featured: Int? = null,
    val sortBy: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)