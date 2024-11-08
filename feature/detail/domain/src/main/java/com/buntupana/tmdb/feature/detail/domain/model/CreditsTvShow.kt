package com.buntupana.tmdb.feature.detail.domain.model

data class CreditsTvShow(
    val castList: List<Person.Cast.TvShow>,
    val crewList: List<Person.Crew.TvShow>
)
