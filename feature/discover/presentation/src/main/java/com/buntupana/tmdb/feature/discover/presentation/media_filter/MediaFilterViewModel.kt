package com.buntupana.tmdb.feature.discover.presentation.media_filter

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
class MediaFilterViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(MediaFilterState())
        private set

    fun onEvent(event: MediaFilterEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
//            when (event) {
//
//            }
        }
    }
}