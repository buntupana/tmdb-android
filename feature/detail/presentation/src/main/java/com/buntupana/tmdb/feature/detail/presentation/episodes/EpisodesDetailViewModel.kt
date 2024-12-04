package com.buntupana.tmdb.feature.detail.presentation.episodes

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
import com.buntupana.tmdb.feature.detail.domain.usecase.GetSeasonDetailsUseCase
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EpisodesDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSeasonDetailsUseCase: GetSeasonDetailsUseCase
) : ViewModel() {

    private val navArgs: EpisodesDetailNav = savedStateHandle.navArgs()

    var state by mutableStateOf(
        EpisodesDetailState(
            tvShowId = navArgs.tvShowId,
            sessionName = navArgs.seasonName,
            seasonNumber = navArgs.seasonNumber,
            posterUrl = navArgs.posterUrl,
            releaseYear = navArgs.releaseYear,
            backgroundColor = Color(navArgs.backgroundColor ?: DetailBackgroundColor.toArgb())
        )
    )
        private set

    init {
        onEvent(EpisodesDetailEvent.GetEpisodesDetail)
    }

    fun onEvent(event: EpisodesDetailEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                EpisodesDetailEvent.GetEpisodesDetail -> getEpisodesDetail()
            }
        }
    }

    private suspend fun getEpisodesDetail() {
        state = state.copy(isLoading = true, isGetEpisodesError = false)

        getSeasonDetailsUseCase(state.tvShowId, state.seasonNumber)
            .onError {
                state = state.copy(isLoading = false, isGetEpisodesError = true)
            }
            .onSuccess {
                state = state.copy(
                    isLoading = false,
                    isGetEpisodesError = false,
                    episodeList = it.episodes
                )
            }
    }
}