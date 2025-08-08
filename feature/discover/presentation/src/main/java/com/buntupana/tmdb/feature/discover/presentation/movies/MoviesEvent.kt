package com.buntupana.tmdb.feature.discover.presentation.movies

import com.buntupana.tmdb.feature.discover.domain.entity.MediaListFilter

sealed class MoviesEvent {
    data class FilterMovies(val mediaListFilter: MediaListFilter) : MoviesEvent()
}