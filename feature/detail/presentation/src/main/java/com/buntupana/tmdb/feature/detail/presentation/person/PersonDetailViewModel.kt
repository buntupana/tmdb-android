package com.buntupana.tmdb.feature.detail.presentation.person

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.util.navArgs
import com.buntupana.tmdb.feature.detail.domain.usecase.GetPersonDetailsUseCase
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import kotlinx.coroutines.launch
import timber.log.Timber

class PersonDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase
) : ViewModel() {

    private val navArgs: PersonDetailNav = savedStateHandle.navArgs()

    var state by mutableStateOf(PersonDetailState())
        private set

    init {
        onEvent(PersonDetailEvent.GetPersonDetails)
    }

    fun onEvent(event: PersonDetailEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                PersonDetailEvent.GetPersonDetails -> getPersonDetails(navArgs.personId)
                is PersonDetailEvent.SelectMediaTypeAndDepartment -> {
                    state = state.copy(
                        mediaTypeSelected = event.mediaType,
                        departmentSelected = event.department
                    )
                }
            }
        }
    }

    private suspend fun getPersonDetails(personId: Long) {

        state = state.copy(isLoading = true, isGetPersonError = false)

        getPersonDetailsUseCase(personId)
            .onError {
                state = state.copy(isLoading = false, isGetPersonError = true)
            }
            .onSuccess {
                state = state.copy(
                    isLoading = false,
                    isGetPersonError = false,
                    personDetails = it
                )
            }
    }
}