package com.buntupana.tmdb.feature.detail.presentation.seasons

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.buntupana.tmdb.core.presentation.theme.DetailBackgroundColor
import com.buntupana.tmdb.feature.detail.presentation.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeasonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val navArgs: SeasonsDetailNavArgs = savedStateHandle.navArgs()

    val state by mutableStateOf(
        SeasonsDetailState(
            mediaId = navArgs.tvShowDetails.id,
            mediaName = navArgs.tvShowDetails.title,
            releaseYear = navArgs.tvShowDetails.releaseDate?.year.toString(),
            posterUrl = navArgs.tvShowDetails.posterUrl,
            backgroundColor = Color(navArgs.backgroundColor ?: DetailBackgroundColor.toArgb()),
            seasonList = navArgs.tvShowDetails.seasonList
        )
    )
}