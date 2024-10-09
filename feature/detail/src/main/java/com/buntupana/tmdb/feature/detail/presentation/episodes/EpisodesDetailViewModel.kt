package com.buntupana.tmdb.feature.detail.presentation.episodes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.presentation.util.navArgs
import com.buntupana.tmdb.feature.detail.domain.usecase.GetSeasonDetailsUseCase
import com.buntupana.tmdb.feature.detail.presentation.seasons.SeasonsDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodesDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSeasonDetailsUseCase: GetSeasonDetailsUseCase
): ViewModel() {

    private val navArgs: EpisodesDetailNavArgs = savedStateHandle.navArgs()

    var state by mutableStateOf(
        SeasonsDetailState(
            tvShowId = navArgs.tvShowId,
            backgroundColor = Color(navArgs.backgroundColor ?: DetailBackgroundColor.toArgb())
        )
    )

    init {

    }
}