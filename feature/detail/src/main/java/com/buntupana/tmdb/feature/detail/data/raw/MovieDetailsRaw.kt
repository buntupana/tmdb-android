package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class MovieDetailsRaw(
    val id: Long,
    val adult: Boolean,
    @field:Json(name = "backdrop_path")
    val backdropPath: String?,
    @field:Json(name = "belongs_to_collection")
    val belongsToCollection: BelongsToCollection,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    @field:Json(name = "imdb_id")
    val imdbId: String,
    @field:Json(name = "original_language")
    val originalLanguage: String,
    @field:Json(name = "original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @field:Json(name = "poster_path")
    val posterPath: String?,
    @field:Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany>,
    @field:Json(name = "production_countries")
    val productionCountries: List<ProductionCountry>,
    @field:Json(name = "release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Long,
    @field:Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @field:Json(name = "vote_average")
    val voteAverage: Double,
    val videos: MediaVideosRaw?,
    @field:Json(name = "release_dates")
    val releaseDates: ReleaseDatesRaw?,
    @field:Json(name = "vote_count")
    val voteCount: Long,
    val credits: CreditsRaw?
)