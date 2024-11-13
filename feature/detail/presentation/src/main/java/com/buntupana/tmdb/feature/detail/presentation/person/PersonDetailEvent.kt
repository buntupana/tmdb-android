package com.buntupana.tmdb.feature.detail.presentation.person

sealed class PersonDetailEvent {
    data object GetPersonDetails: PersonDetailEvent()
}
