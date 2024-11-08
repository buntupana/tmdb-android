package com.buntupana.tmdb.feature.detail.presentation.person

import com.buntupana.tmdb.feature.detail.domain.model.PersonFullDetails

data class PersonDetailState(
    val isLoading: Boolean = false,
    val isGetPersonError: Boolean = false,
    val personDetails: PersonFullDetails? = null
)
