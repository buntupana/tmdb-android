package com.buntupana.tmdb.feature.detail.presentation.person

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.buntupana.tmdb.feature.detail.presentation.PersonDetailNavArgs
import com.buntupana.tmdb.feature.detail.presentation.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    val navArgs: PersonDetailNavArgs = savedStateHandle.navArgs()

    var state by mutableStateOf(PersonState())


}