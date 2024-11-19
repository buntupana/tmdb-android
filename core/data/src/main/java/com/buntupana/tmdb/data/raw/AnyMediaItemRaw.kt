package com.buntupana.tmdb.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This media item can be a Movie or Tv Show or a Person, this class is used when the api
 * returns a mix of everything
 * */
@Serializable
data class AnyMediaItemRaw(
    val id: Long,
    @SerialName("media_type")
    val mediaType: String,
    val overview: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int>? = null,
    val popularity: Float? = null,
    @SerialName("vote_average")
    val voteAverage: Float? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null,
    // Movie fields
    val title: String? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    val video: Boolean? = null,
    val adult: Boolean? = null,
    // TV_Show fields
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    @SerialName("origin_country")
    val originCountry: List<String>? = null,
    // Person fields
    val name: String? = null,
    @SerialName("profile_path")
    val profilePath: String? = null,
    val gender: Int? = null,
    @SerialName("known_for_department")
    val knownForDepartment: String? = null,
    @SerialName("known_for")
    val knownFor: List<KnownFor>? = null,
)
