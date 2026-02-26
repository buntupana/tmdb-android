package com.buntupana.tmdb.feature.lists.data.remote_data_source.raw


import com.buntupana.tmdb.core.data.raw.MediaItemRaw
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListDetailRaw(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("item_count")
    val itemCount: Int,
    @SerialName("page")
    val page: Int,
    @SerialName("average_rating")
    val averageRating: Float,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("iso_3166_1")
    val iso31661: String,
    @SerialName("iso_639_1")
    val iso6391: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("public")
    val public: Boolean,
    @SerialName("revenue")
    val revenue: Long?,
    @SerialName("runtime")
    val runtime: Int?,
    @SerialName("sort_by")
    val sortBy: String,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int,
    @SerialName("results")
    val results: List<MediaItemRaw>
)