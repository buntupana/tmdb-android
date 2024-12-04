package com.buntupana.tmdb.feature.discover.presentation.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(MoviesState())
        private set

    fun onEvent(event: MoviesEvent) {
        Timber.d("onEvent() called with: event = []")
        viewModelScope.launch {
//            when (event) {
//
//            }
        }
    }
}