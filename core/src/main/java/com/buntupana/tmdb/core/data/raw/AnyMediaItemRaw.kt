package com.buntupana.tmdb.core.data.raw

import com.squareup.moshi.Json

/**
 * This media item can be a Movie or Tv Show or a Person, this class is used when the api
 * returns a mix of everything
 * */
data class AnyMediaItemRaw(
    val id: Long,
    @Json(name = "media_type")
    val mediaType: String,
    val overview: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "original_language")
    val originalLanguage: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>?,
    val popularity: Float?,
    @Json(name = "vote_average")
    val voteAverage: Float?,
    @Json(name = "vote_count")
    val voteCount: Int?,
    // Movie fields
    val title: String?,
    @Json(name = "original_title")
    val originalTitle: String?,
    @Json(name = "release_date")
    val releaseDate: String?,
    val video: Boolean?,
    val adult: Boolean?,
    // TV_Show fields
    @Json(name = "original_name")
    val originalName: String?,
    @Json(name = "first_air_date")
    val firstAirDate: String?,
    @Json(name = "origin_country")
    val originCountry: List<String>?,
    // Person fields
    val name: String?,
    @Json(name = "profile_path")
    val profilePath: String?,
    val gender: Int?,
    @Json(name = "known_for_department")
    val knownForDepartment: String?,
    @Json(name = "known_for")
    val knownFor: List<KnownFor>?,
)
