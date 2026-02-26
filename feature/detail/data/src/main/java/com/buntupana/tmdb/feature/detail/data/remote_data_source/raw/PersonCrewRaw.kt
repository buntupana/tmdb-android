package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonCrewRaw(
    val id: Long,
    val adult: Boolean? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("credit_id")
    val creditId: String,
    val department: String,
    @SerialName("episode_count")
    val episodeCount: Int? = null,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int>? = null,
    val job: String? = null,
    @SerialName("media_type")
    val mediaType: String,
    val name: String? = null,
    @SerialName("origin_country")
    val originCountry: List<String>? = null,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    val overview: String,
    val popularity: Float,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("vote_count")
    val voteCount: Int? = null
)