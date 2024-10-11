package com.buntupana.tmdb.feature.search.presentation

import com.buntupana.tmdb.feature.search.domain.model.SearchItem

val searchItemMovieSample = SearchItem.Movie(
    id = 0,
    name = "Terminator",
    imageUrl = null,
    originalName = "Terminator Origin",
    popularity = 10f,
    voteAverage = 20,
    originalLanguage = null,
    voteCount = 10,
)

val searchItemTVShowSample = SearchItem.TvShow(
    id = 0,
    name = "Terminator",
    imageUrl = null,
    originalName = "Terminator Origin",
    popularity = 10f,
    voteAverage = 20,
    voteCount = 10,
)

val searchItemPersonSample = SearchItem.Person(
    id = 0,
    name = "Terminator",
    imageUrl = null,
    originalName = "Terminator Origin",
    popularity = 10f
)