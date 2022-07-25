package com.buntupana.tmdb.feature.detail.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.feature.detail.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    val navArgs: MediaDetailNavArgs = savedStateHandle.navArgs()

    var state by mutableStateOf(DetailScreenState())

    init {
        when (navArgs.mediaType) {
            MediaType.MOVIE -> {
                getMovieDetails()
            }
            MediaType.TV_SHOW -> TODO()
        }
    }

    private fun getMovieDetails() {
        viewModelScope.launch {
            getMovieDetailsUseCase(navArgs.mediaId) {
                loading {
                    state = state.copy(isLoading = true)
                }
                error {
                    state = state.copy(isLoading = false)
                }
                success {
                    state = state.copy(isLoading = false, movieDetails = it)
                }
            }
        }
    }
}