package com.buntupana.tmdb.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieItemRaw(
    val id: Long,
    val title: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    val adult: Boolean,
    @SerialName("genre_ids")
    val genreIds: List<Int>? = null,
    val popularity: Float? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    val video: Boolean? = null,
    @SerialName("vote_average")
    val voteAverage: Float? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null
)