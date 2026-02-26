package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    @SerialName("aspect_ratio")
    val aspectRatio: Double,
    @SerialName("file_path")
    val filePath: String,
    @SerialName("height")
    val height: Int,
    @SerialName("iso_639_1")
    val iso6391: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("width")
    val width: Int
)