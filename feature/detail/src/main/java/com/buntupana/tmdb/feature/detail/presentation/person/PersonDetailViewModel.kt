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
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase
) : ViewModel() {

    val navArgs: PersonDetailNavArgs = savedStateHandle.navArgs()

    var state by mutableStateOf(PersonState())

    init {
        getPersonDetails(navArgs.personId)
    }

    private fun getPersonDetails(personId: Long) {
        viewModelScope.launch {
            getPersonDetailsUseCase(personId) {
                loading {
                    state = state.copy(isLoading = true)
                }
                error {
                    state = state.copy(isLoading = false)
                }
                success { personDetails ->
                    state = state.copy(isLoading = false, personDetails = personDetails)
                }
            }
        }
    }
}