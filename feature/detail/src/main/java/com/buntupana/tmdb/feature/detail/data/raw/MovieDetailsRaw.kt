package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class MovieDetailsRaw(
    val id: Long,
    val adult: Boolean,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "belongs_to_collection")
    val belongsToCollection: BelongsToCollection?,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    @Json(name = "imdb_id")
    val imdbId: String?,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany>,
    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountry>,
    @Json(name = "release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Long,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean?,
    @Json(name = "vote_average")
    val voteAverage: Double,
    val videos: MediaVideosRaw?,
    @Json(name = "release_dates")
    val releaseDates: ReleaseDatesRaw?,
    @Json(name = "vote_count")
    val voteCount: Int?,
    val credits: CreditsMovieRaw?,
    val recommendations: RecommendationsRaw
)