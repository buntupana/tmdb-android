package com.buntupana.tmdb.feature.discover.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.feature.discover.domain.usecase.GetPopularMoviesStreaming
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getPopularMoviesStreaming: GetPopularMoviesStreaming
) : ViewModel() {

    init {
        getPopularStreaming()
    }

    private fun getPopularStreaming() {
        viewModelScope.launch {
            getPopularMoviesStreaming(Unit) {
                loading { }
                error {

                }
                success { data ->

                }
            }
        }
    }
}