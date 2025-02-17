package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowDetailsRaw(
    val id: Long,
    val name: String? = null,
    val overview: String? = null,
    val tagline: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    val genres: List<Genre>,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    val adult: Boolean? = null,
    @SerialName("created_by")
    val createdBy: List<CreatedBy>,
    @SerialName("episode_run_time")
    val episodeRunTime: List<Long>,
    val homepage: String? = null,
    val inProduction: Boolean? = null,
    val languages: List<String>? = null,
    @SerialName("last_air_date")
    val lastAirDate: String? = null,
    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: EpisodeRaw? = null,
    val networks: List<Network>,
    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: EpisodeRaw? = null,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int? = null,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int? = null,
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("original_language")
    val originalLanguageCode: String? = null,
    @SerialName("original_name")
    val originalName: String? = null,
    val popularity: Float? = null,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry>,
    val seasons: List<SeasonRaw>,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String? = null,
    val type: String? = null,
    @SerialName("vote_average")
    val voteAverage: Float? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null,
    val videos: MediaVideosRaw? = null,
    @SerialName("content_ratings")
    val contentRatings: ContentRatingsRaw? = null,
    @SerialName("aggregate_credits")
    val credits: CreditsTvShowRaw? = null,
    val recommendations: RecommendationsRaw,
    @SerialName("account_states")
    val accountStates: MediaAccountStateRaw? = null,
    @SerialName("external_ids")
    val externalLinks: ExternalLinksRaw? = null
)