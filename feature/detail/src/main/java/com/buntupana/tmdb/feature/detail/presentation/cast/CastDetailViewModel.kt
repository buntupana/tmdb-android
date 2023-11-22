package com.buntupana.tmdb.feature.detail.presentation.cast

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
class CastDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val navArgs: CastDetailNavArgs = savedStateHandle.navArgs()

    val state by mutableStateOf(
        CastDetailState(
            mediaName = navArgs.mediaDetails.title,
            releaseYear = navArgs.mediaDetails.releaseDate?.year.toString(),
            posterUrl = navArgs.mediaDetails.posterUrl,
            backgroundColor = Color(navArgs.backgroundColor ?: DetailBackgroundColor.toArgb()),
            personCastList = navArgs.mediaDetails.castList,
            personCrewMap = navArgs.mediaDetails.crewList.groupBy { it.department }.toSortedMap()
        )
    )
}