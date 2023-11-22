package com.buntupana.tmdb.feature.detail.domain.model

data class Credits(
    val castList: List<Person.Cast>,
    val crewList: List<Person.Crew>
)
