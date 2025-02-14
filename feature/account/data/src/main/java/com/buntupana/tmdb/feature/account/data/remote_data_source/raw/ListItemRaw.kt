package com.buntupana.tmdb.feature.account.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListItemRaw(
    val id: Long,
    val name: String,
    @SerialName("account_object_id")
    val accountObjectId: String,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    val adult: Int?,
    @SerialName("average_rating")
    val averageRating: Float?,
    @SerialName("created_at")
    val createdAt: String,
    val description: String?,
    val featured: Int?,
    val iso_3166_1: String,
    val iso_639_1: String,
    @SerialName("number_of_items")
    val numberOfItems: Int,
    @SerialName("poster_path")
    val posterPath: String?,
    val public: Int?,
    val revenue: Long?,
    val runtime: String?,
    @SerialName("sort_by")
    val sortBy: Int,
    @SerialName("updated_at")
    val updatedAt: String
)