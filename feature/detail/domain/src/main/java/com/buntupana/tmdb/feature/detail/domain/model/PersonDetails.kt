package com.buntupana.tmdb.feature.detail.domain.model

import java.time.LocalDate

data class PersonDetails(
    val id: Long,
    val name: String,
    val profileUrl: String?,
    val homePageUrl: String?,
    val knownForDepartment: String,
    val gender: com.panabuntu.tmdb.core.common.model.Gender,
    val birthDate: LocalDate?,
    val deathDate: LocalDate?,
    val age: Int,
    val placeOfBirth: String,
    val biography: String,
    val externalLinkList: List<ExternalLink>,
    val filmography: List<CreditPersonItem>
)
