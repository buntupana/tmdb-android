package com.buntupana.tmdb.feature.detail.presentation.person

sealed class PersonDetailEvent {
    object GetPersonDetails: PersonDetailEvent()
}
