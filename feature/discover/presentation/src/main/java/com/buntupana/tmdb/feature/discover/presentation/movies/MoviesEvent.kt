package com.buntupana.tmdb.feature.discover.presentation.movies

import com.buntupana.tmdb.feature.discover.domain.entity.MediaFilter

sealed class MoviesEvent {
    data class FilterMovies(val mediaFilter: MediaFilter) : MoviesEvent()
}