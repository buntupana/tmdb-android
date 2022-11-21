package com.buntupana.tmdb.feature.detail.presentation.person

import com.buntupana.tmdb.feature.detail.domain.model.PersonFullDetails

data class PersonState(
    val isLoading: Boolean = false,
    val personDetails: PersonFullDetails? = null
)
