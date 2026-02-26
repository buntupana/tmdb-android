package com.buntupana.tmdb.feature.detail.domain.model

data class CreditsMovie(
    val castList: List<Person.Cast.Movie>,
    val crewList: List<Person.Crew.Movie>
)
