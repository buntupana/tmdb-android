package com.buntupana.tmdb.feature.discover.data.mapper

import com.buntupana.tmdb.feature.discover.data.raw.MovieItemRaw
import com.buntupana.tmdb.feature.discover.domain.model.MovieItem

fun MovieItemRaw.toModel(): MovieItem {
    return MovieItem(
        id,
        title,
        originalTitle,
        overview,
        posterPath.orEmpty(),
        backdropPath.orEmpty(),
        originalLanguage,
        genreIds,
        popularity,
        voteAverage,
        voteCount,
        releaseDate,
        video,
        adult
    )
}