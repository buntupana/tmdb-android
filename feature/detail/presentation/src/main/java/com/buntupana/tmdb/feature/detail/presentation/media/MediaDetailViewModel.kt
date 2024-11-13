package com.buntupana.tmdb.feature.detail.presentation.media

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.feature.detail.domain.usecase.GetMovieDetailsUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.GetTvShowDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MediaDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase
) : ViewModel() {

    private val navArgs: MediaDetailNav = savedStateHandle.toRoute()

    var state by mutableStateOf(
        MediaDetailState(
            mediaId = navArgs.mediaId,
            mediaType = navArgs.mediaType,
            backgroundColor = Color(navArgs.backgroundColor ?: DetailBackgroundColor.toArgb())
        )
    )

    init {
        onEvent(MediaDetailEvent.GetMediaDetails)
    }

    fun onEvent(event: MediaDetailEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        when(event) {
            MediaDetailEvent.GetMediaDetails -> {
                when (navArgs.mediaType) {
                    MediaType.MOVIE -> getMovieDetails()
                    MediaType.TV_SHOW -> getTvShowDetails()
                }
            }
        }
    }

    private fun getMovieDetails() {
        viewModelScope.launch {
            getMovieDetailsUseCase(navArgs.mediaId) {
                loading {
                    state = state.copy(isLoading = true, isGetContentError = false)
                }
                error {
                    state = state.copy(isLoading = false, isGetContentError = true)
                }
                success {
                    state = state.copy(isLoading = false, isGetContentError = false, mediaDetails = it)
                }
            }
        }
    }

    private fun getTvShowDetails() {
        viewModelScope.launch {
            getTvShowDetailsUseCase(navArgs.mediaId) {
                loading {
                    state = state.copy(isLoading = true, isGetContentError = false)
                }
                error {
                    state = state.copy(isLoading = false, isGetContentError = true)
                }
                success {
                    state = state.copy(isLoading = false, isGetContentError = false, mediaDetails = it)
                }
            }
        }
    }
}