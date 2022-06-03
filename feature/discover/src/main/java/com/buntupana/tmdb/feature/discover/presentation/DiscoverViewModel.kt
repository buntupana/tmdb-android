package com.buntupana.tmdb.feature.discover.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import com.buntupana.tmdb.feature.discover.domain.usecase.GetPopularMoviesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getPopularMoviesListUseCase: GetPopularMoviesListUseCase
) : ViewModel() {

    var state by mutableStateOf(DiscoverState())
    private var getPopularMoviesJob: Job? = null

    val popularFilterSet by mutableStateOf(
        setOf(
            PopularFilter.Streaming,
            PopularFilter.OnTv,
            PopularFilter.ForRent,
            PopularFilter.InTheatres,
        )
    )

    var popularFilterSelected = 0

    init {
        getPopularMovies(PopularType.STREAMING)
    }

    fun onEvent(event: DiscoverEvent) {
        when (event) {
            is DiscoverEvent.ChangePopularType -> {
                getPopularMovies(event.popularType)
            }
        }
    }

    private fun getPopularMovies(popularType: PopularType) {

        getPopularMoviesJob?.cancel()
        getPopularMoviesJob = viewModelScope.launch {
            getPopularMoviesListUseCase(popularType) {
                loading {
                    state = state.copy(isLoading = true)
                }
                error {

                }
                success { data ->
                    state = state.copy(isLoading = false, popularMediaItemList = data)
                }
            }
        }
    }
}