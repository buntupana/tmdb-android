package com.buntupana.tmdb.feature.detail.domain.model

import com.buntupana.tmdb.core.domain.model.Gender
import java.time.LocalDate

data class PersonDetails(
    val id: Long,
    val name: String,
    val profileUrl: String?,
    val homePageUrl: String,
    val imdbLink: String,
    val knownForDepartment: String,
    val gender: Gender,
    val birthDate: LocalDate?,
    val deathDate: LocalDate?,
    val age: Int,
    val placeOfBirth: String,
    val biography: String,
    val externalLinkList: List<ExternalLink>,
    val filmography: List<CreditPersonItem>
)
