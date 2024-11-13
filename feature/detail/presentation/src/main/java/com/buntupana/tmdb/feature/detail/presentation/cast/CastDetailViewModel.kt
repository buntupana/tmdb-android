package com.buntupana.tmdb.feature.detail.presentation.cast

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.buntupana.tmdb.core.ui.theme.DetailBackgroundColor
import com.buntupana.tmdb.feature.detail.domain.usecase.GetMovieCreditsUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.GetTvShowCreditsUseCase
import com.panabuntu.tmdb.core.common.entity.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CastDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val getTvShowCreditsUseCase: GetTvShowCreditsUseCase
) : ViewModel() {

    private val navArgs: CastDetailNav = savedStateHandle.toRoute()

    var state by mutableStateOf(
        CastDetailState(
            mediaId = navArgs.mediaId,
            mediaType = navArgs.mediaType,
            mediaName = navArgs.mediaTitle,
            releaseYear = navArgs.releaseYear,
            posterUrl = navArgs.posterUrl,
            backgroundColor = Color(navArgs.backgroundColor ?: DetailBackgroundColor.toArgb()),
        )
    )

    init {
        onEvent(CastDetailEvent.GetCredits)
    }

    fun onEvent(event: CastDetailEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                CastDetailEvent.GetCredits -> {
                    when (state.mediaType) {
                        MediaType.MOVIE -> getMovieCredits()
                        MediaType.TV_SHOW -> getTvShowCredits()
                    }
                }
            }
        }
    }

    private suspend fun getMovieCredits() {
        getMovieCreditsUseCase(
            parameters = state.mediaId,
            loading = {
                state = state.copy(isLoading = true, isGetContentError = false)
            },
            error = {
                state = state.copy(isLoading = false, isGetContentError = true)
            },
            success = {
                state = state.copy(
                    isLoading = false,
                    isGetContentError = false,
                    personCastList = it.personCastList,
                    personCrewMap = it.personCrewMap
                )
            }
        )
    }

    private suspend fun getTvShowCredits() {
        getTvShowCreditsUseCase(
            parameters = state.mediaId,
            loading = {
                state = state.copy(isLoading = true, isGetContentError = false)
            },
            error = {
                state = state.copy(isLoading = false, isGetContentError = true)
            },
            success = {
                state = state.copy(
                    isLoading = false,
                    isGetContentError = false,
                    personCastList = it.personCastList,
                    personCrewMap = it.personCrewMap
                )
            }
        )
    }
}