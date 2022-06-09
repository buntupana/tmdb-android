package com.buntupana.tmdb.core.domain.model

sealed class MediaItem(
    val id: Long,
    val name: String,
    val posterPath: String,
    val popularity: Double,
) {

    class Movie(
        id: Long,
        name: String,
        val originalName: String,
        val overview: String,
        posterPath: String,
        val backdropPath: String,
        val originalLanguage: String,
        val genreIds: List<Int>,
        popularity: Double,
        val voteAverage: Int,
        val voteCount: Int,
        val releaseDate: String,
        val video: Boolean,
        val adult: Boolean
    ) : MediaItem(
        id,
        name,
        posterPath,
        popularity
    )

    class TvShow(
        id: Long,
        name: String,
        val originalName: String,
        val overview: String,
        posterPath: String,
        val backdropPath: String,
        val originalLanguage: String,
        val genreIds: List<Int>,
        popularity: Double,
        val voteAverage: Int,
        val voteCount: Int,
        val releaseDate: String,
        val originCountry: List<String>
    ) : MediaItem(
        id,
        name,
        posterPath,
        popularity
    )

    class Person(
        id: Long,
        name: String,
        posterPath: String,
        popularity: Double,
        val adult: Boolean,
        val gender: Int
    ) : MediaItem(
        id,
        name,
        posterPath,
        popularity
    )

    object Unknown : MediaItem(
        0,
        "",
        "",
        0.0
    )
}
