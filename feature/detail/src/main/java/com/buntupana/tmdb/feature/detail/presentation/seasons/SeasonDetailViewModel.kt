package com.buntupana.tmdb.feature.detail.presentation.seasons

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.presentation.util.navArgs
import com.buntupana.tmdb.feature.detail.domain.usecase.GetTvShowSeasonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SeasonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTvShowSeasonsUseCase: GetTvShowSeasonsUseCase
) : ViewModel() {

    private val navArgs: SeasonsDetailNavArgs = savedStateHandle.navArgs()

    var state by mutableStateOf(
        SeasonsDetailState(
            tvShowId = navArgs.tvShowId,
            tvShowName = navArgs.tvShowName,
            releaseYear = navArgs.releaseYear,
            posterUrl = navArgs.posterUrl,
            backgroundColor = Color(navArgs.backgroundColor ?: DetailBackgroundColor.toArgb())
        )
    )

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
        getTvShowSeasonsUseCase(
            parameters = state.tvShowId,
            loading = {
                state = state.copy(isLoading = true, isGetSeasonsError = false)
            },
            error = {
                state = state.copy(isLoading = false, isGetSeasonsError = true)
            },
            success = { seasonList ->
                state = state.copy(isLoading = false, isGetSeasonsError = false, seasonList = seasonList)
            }
        )
    }
}