package com.buntupana.tmdb.feature.detail.presentation.episodes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.presentation.util.decodeUrl
import com.buntupana.tmdb.core.presentation.util.navArgs
import com.buntupana.tmdb.feature.detail.domain.usecase.GetSeasonDetailsUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.GetSeasonDetailsUseCaseParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EpisodesDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSeasonDetailsUseCase: GetSeasonDetailsUseCase
) : ViewModel() {

    private val navArgs: EpisodesDetailNavArgs = savedStateHandle.navArgs()

    var state by mutableStateOf(
        EpisodesDetailState(
            tvShowId = navArgs.tvShowId,
            sessionName = navArgs.seasonName,
            seasonNumber = navArgs.seasonNumber,
            posterUrl = navArgs.posterUrlEncoded?.decodeUrl(),
            releaseYear = navArgs.releaseYear,
            backgroundColor = Color(navArgs.backgroundColor ?: DetailBackgroundColor.toArgb())
        )
    )

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
        getSeasonDetailsUseCase(
            parameters = GetSeasonDetailsUseCaseParams(state.tvShowId, state.seasonNumber),
            loading = {
                state = state.copy(isLoading = true, isGetEpisodesError = false)
            },
            error = {
                state = state.copy(isLoading = false, isGetEpisodesError = true)
            },
            success = { response ->
                state = state.copy(
                    isLoading = false,
                    isGetEpisodesError = false,
                    episodeList = response.episodes
                )
            }
        )
    }
}