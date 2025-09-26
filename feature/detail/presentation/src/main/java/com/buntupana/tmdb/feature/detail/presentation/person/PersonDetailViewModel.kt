package com.buntupana.tmdb.feature.detail.presentation.person

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.snackbar.SnackbarController
import com.buntupana.tmdb.core.ui.snackbar.SnackbarEvent
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.core.ui.util.navArgs
import com.buntupana.tmdb.feature.detail.domain.usecase.GetPersonDetailsUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.GetPersonImagesUseCase
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

import com.buntupana.tmdb.core.ui.R as RCore

class PersonDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val getPersonImagesUseCase: GetPersonImagesUseCase
) : ViewModel() {

    private val navArgs: PersonDetailRoute = savedStateHandle.navArgs()

    var state by mutableStateOf(PersonDetailState())
        private set

    private var getProfileImagesJob: Job? = null

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

                PersonDetailEvent.ShowProfileImages -> {
                    getProfileImages()
                }

                PersonDetailEvent.DismissImageViewer -> {
                    getProfileImagesJob?.cancel()
                    state = state.copy(showImageViewer = false)

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

    private fun getProfileImages() {
        state = state.copy(showImageViewer = true, imageList = null)
        getProfileImagesJob?.cancel()
        getProfileImagesJob = viewModelScope.launch {
            getPersonImagesUseCase(navArgs.personId)
                .onError {
                    state = state.copy(showImageViewer = false)
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            UiText.StringResource(RCore.string.common_loading_content_error)
                        )
                    )
                }.onSuccess { profileImageList ->
                    state = state.copy(showImageViewer = true, imageList = profileImageList)
                }
        }
    }
}