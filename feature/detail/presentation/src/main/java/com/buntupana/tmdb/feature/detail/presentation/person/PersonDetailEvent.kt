package com.buntupana.tmdb.feature.detail.presentation.person

sealed class PersonDetailEvent {
    data object GetPersonDetails : PersonDetailEvent()
    data class SelectMediaTypeAndDepartment(
        val mediaType: Int?,
        val department: String?
    ) : PersonDetailEvent()
}
