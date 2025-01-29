package com.buntupana.tmdb.feature.account.data.raw

import kotlinx.serialization.SerialName

data class ListItemRaw(
    val id: Int,
    val name: String,
    @SerialName("account_object_id")
    val account_object_id: String,
    @SerialName("backdrop_path")
    val backdrop_path: String?,
    val adult: Int,
    @SerialName("average_rating")
    val average_rating: Float,
    @SerialName("created_at")
    val created_at: String,
    val description: String,
    val featured: Int,
    val iso_3166_1: String,
    val iso_639_1: String,
    @SerialName("number_of_items")
    val number_of_items: Int,
    @SerialName("avatar_path")
    val poster_path: String?,
    val public: Int,
    val revenue: Int,
    val runtime: String,
    @SerialName("sort_by")
    val sort_by: Int,
    @SerialName("updated_at")
    val updated_at: String
)