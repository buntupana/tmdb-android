package com.buntupana.tmdb.feature.detail.domain.model

import com.buntupana.tmdb.core.domain.model.Gender
import org.threeten.bp.LocalDate

data class PersonFullDetails(
    val id: Long,
    val name: String,
    val profileUrl: String,
    val homePageUrl: String,
    val knownForDepartment: String,
    val gender: Gender,
    val birthDate: LocalDate?,
    val deathDate: LocalDate?,
    val placeOfBirth: String,
    val biography: String,
    val externalLinks: ExternalLinks,
    val creditList: List<CreditPersonItem>
)
