package com.buntupana.tmdb.feature.detail.presentation.media

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.ui.util.navArgs
import com.buntupana.tmdb.feature.detail.domain.usecase.GetMovieDetailsUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.GetTvShowDetailsUseCase
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
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

    private val navArgs: MediaDetailNav = savedStateHandle.navArgs()

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
        viewModelScope.launch {
            when (event) {
                MediaDetailEvent.GetMediaDetails -> {
                    when (navArgs.mediaType) {
                        MediaType.MOVIE -> getMovieDetails()
                        MediaType.TV_SHOW -> getTvShowDetails()
                    }
                }
            }
        }
    }

    private suspend fun getMovieDetails() {
        state = state.copy(isLoading = true, isGetContentError = false)

        getMovieDetailsUseCase(navArgs.mediaId)
            .onError {
                state = state.copy(isLoading = false, isGetContentError = true)
            }
            .onSuccess {
                state = state.copy(isLoading = false, isGetContentError = false, mediaDetails = it)
            }
    }

    private suspend fun getTvShowDetails() {
        state = state.copy(isLoading = true, isGetContentError = false)

        getTvShowDetailsUseCase(navArgs.mediaId)
            .onError {
                state = state.copy(isLoading = false, isGetContentError = true)
            }
            .onSuccess {
                state =
                    state.copy(isLoading = false, isGetContentError = false, mediaDetails = it)
            }
    }
}