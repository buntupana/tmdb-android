package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class PersonDetailsRaw(
    val id: Long,
    val name: String,
    val adult: Boolean,
    @Json(name = "also_known_as")
    val alsoKnownAs: List<String>,
    val biography: String?,
    val birthday: String?,
    @Json(name = "deathday")
    val deathDay: String?,
    val gender: Int,
    val homepage: String?,
    @Json(name = "imdb_id")
    val imdbId: String?,
    @Json(name = "known_for_department")
    val knownForDepartment: String?,
    @Json(name = "place_of_birth")
    val placeOfBirth: String?,
    val popularity: Double,
    @Json(name = "profile_path")
    val profilePath: String?,
    @Json(name = "combined_credits")
    val combinedCredits: FilmographyRaw?,
    @Json(name = "external_ids")
    val externalLinks: ExternalLinksRaw?
)