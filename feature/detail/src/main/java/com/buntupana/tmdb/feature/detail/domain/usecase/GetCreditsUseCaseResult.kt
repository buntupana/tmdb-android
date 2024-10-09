package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.feature.detail.domain.model.Person

data class GetCreditsUseCaseResult (
    val personCastList: List<Person.Cast>,
    val personCrewMap: Map<String, List<Person.Crew>>
)