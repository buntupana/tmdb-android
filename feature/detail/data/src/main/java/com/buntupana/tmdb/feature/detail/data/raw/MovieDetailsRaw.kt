package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRaw(
    val id: Long,
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    @SerialName("imdb_id")
    val imdbId: String? = null,
    @SerialName("original_language")
    val originalLanguageCode: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerialName("release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Long,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean? = null,
    @SerialName("vote_average")
    val voteAverage: Double,
    val videos: MediaVideosRaw? = null,
    @SerialName("release_dates")
    val releaseDates: ReleaseDatesRaw? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null,
    val credits: CreditsMovieRaw? = null,
    val recommendations: RecommendationsRaw,
    @SerialName("account_states")
    val accountStates: MediaAccountStateRaw? = null,
    @SerialName("external_ids")
    val externalLinks: ExternalLinksRaw? = null
)