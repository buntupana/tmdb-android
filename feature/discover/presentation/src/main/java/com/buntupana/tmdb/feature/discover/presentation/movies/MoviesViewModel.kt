package com.buntupana.tmdb.feature.discover.presentation.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.buntupana.tmdb.feature.discover.domain.entity.MediaListFilter
import com.buntupana.tmdb.feature.discover.domain.usecase.GetFilteredMoviesPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getFilteredMoviesPagingUseCase: GetFilteredMoviesPagingUseCase
) : ViewModel() {

    var state by mutableStateOf(MoviesState())
        private set

    init {
        onEvent(MoviesEvent.FilterMovies(mediaListFilter = state.mediaListFilter))
    }

    fun onEvent(event: MoviesEvent) {
        Timber.d("onEvent() called with: event = $event")
        viewModelScope.launch {
            when (event) {
                is MoviesEvent.FilterMovies -> {
                    getMovies(event.mediaListFilter)
                }
            }
        }
    }

    private suspend fun getMovies(mediaListFilter: MediaListFilter) {

        if (state.movieItems != null && state.mediaListFilter == mediaListFilter) return

        val movieFilter = when (mediaListFilter) {
            MediaFilterMovieDefault.popular -> MovieFilter.POPULAR
            MediaFilterMovieDefault.nowPlaying -> MovieFilter.NOW_PLAYING
            MediaFilterMovieDefault.topRated -> MovieFilter.TOP_RATED
            MediaFilterMovieDefault.upcoming -> MovieFilter.UPCOMING
            else -> MovieFilter.CUSTOM
        }

        state = state.copy(mediaListFilter = mediaListFilter, movieFilter = movieFilter, movieItems = null)
        getFilteredMoviesPagingUseCase(mediaListFilter = state.mediaListFilter).let {
            state = state.copy(movieItems = it.cachedIn(viewModelScope))
        }
    }
}