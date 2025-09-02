package com.buntupana.tmdb.feature.detail.presentation.seasons

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
import com.buntupana.tmdb.feature.detail.domain.usecase.GetTvShowSeasonsUseCase
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import kotlinx.coroutines.launch
import timber.log.Timber

class SeasonDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTvShowSeasonsUseCase: GetTvShowSeasonsUseCase
) : ViewModel() {

    private val navArgs: SeasonsDetailNav = savedStateHandle.navArgs()

    var state by mutableStateOf(
        SeasonsDetailState(
            tvShowId = navArgs.tvShowId,
            tvShowName = navArgs.tvShowName,
            releaseYear = navArgs.releaseYear,
            posterUrl = navArgs.posterUrl,
            backgroundColor = Color(navArgs.backgroundColor ?: DetailBackgroundColor.toArgb())
        )
    )
        private set

    init {
        onEvent(SeasonsDetailEvent.GetSeasons)
    }

    fun onEvent(event: SeasonsDetailEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                is SeasonsDetailEvent.GetSeasons -> getSeasons()
            }
        }
    }

    private suspend fun getSeasons() {
        state = state.copy(isLoading = true, isGetSeasonsError = false)

        getTvShowSeasonsUseCase(state.tvShowId)
            .onError {
                state = state.copy(isLoading = false, isGetSeasonsError = true)
            }
            .onSuccess {
                state = state.copy(isLoading = false, isGetSeasonsError = false, seasonList = it)
            }
    }
}