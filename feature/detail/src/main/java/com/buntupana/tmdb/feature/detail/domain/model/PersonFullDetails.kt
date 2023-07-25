package com.buntupana.tmdb.feature.detail.domain.model

import com.buntupana.tmdb.core.domain.model.Gender
import java.time.LocalDate

data class PersonFullDetails(
    val id: Long,
    val name: String,
    val profileUrl: String,
    val knownForDepartment: String,
    val gender: Gender,
    val birthDate: LocalDate?,
    val deathDate: LocalDate?,
    val age: Int,
    val placeOfBirth: String,
    val biography: String,
    val externalLinks: List<ExternalLink>,
    val knownFor: List<CreditPersonItem>,
    val creditMap: Map<String, List<CreditPersonItem>>,
    val knownCredits: Int
)
