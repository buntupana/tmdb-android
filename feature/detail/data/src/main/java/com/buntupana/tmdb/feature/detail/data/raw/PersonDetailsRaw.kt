package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDetailsRaw(
    val id: Long,
    val name: String,
    val adult: Boolean,
    @SerialName("also_known_as")
    val alsoKnownAs: List<String>,
    val biography: String? = null,
    val birthday: String? = null,
    @SerialName("deathday")
    val deathDay: String? = null,
    val gender: Int,
    val homepage: String? = null,
    @SerialName("imdb_id")
    val imdbId: String? = null,
    @SerialName("known_for_department")
    val knownForDepartment: String? = null,
    @SerialName("place_of_birth")
    val placeOfBirth: String? = null,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String? = null,
    @SerialName("combined_credits")
    val combinedCredits: FilmographyRaw? = null,
    @SerialName("external_ids")
    val externalLinks: ExternalLinksRaw? = null
)