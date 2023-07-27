package com.buntupana.tmdb.feature.detail.presentation.person

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.feature.detail.domain.usecase.GetPersonDetailsUseCase
import com.buntupana.tmdb.feature.detail.presentation.PersonDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase
) : ViewModel() {

    private val navArgs: PersonDetailNavArgs = savedStateHandle.navArgs()

    var state by mutableStateOf(PersonDetailState())

    init {
        onEvent(PersonDetailEvent.GetPersonDetails)
    }

    fun onEvent(event: PersonDetailEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                PersonDetailEvent.GetPersonDetails -> getPersonDetails(navArgs.personId)
            }
        }
    }

    private suspend fun getPersonDetails(personId: Long) {
        getPersonDetailsUseCase(personId) {
            loading {
                state = state.copy(isLoading = true, isGetPersonError = false)
            }
            error {
                state = state.copy(isLoading = false, isGetPersonError = true)
            }
            success { personDetails ->
                state = state.copy(
                    isLoading = false,
                    isGetPersonError = false,
                    personDetails = personDetails
                )
            }
        }
    }
}