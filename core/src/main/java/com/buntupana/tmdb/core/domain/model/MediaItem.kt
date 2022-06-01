package com.buntupana.tmdb.core.domain.model

sealed class MediaItem(
    val id: Long,
    val name: String,
    val originalName: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val originalLanguage: String,
    val genreIds: List<Int>,
    val popularity: Double,
    val voteAverage: Int,
    val voteCount: Int,
    val releaseDate: String
) {

    class Movie(
        id: Long,
        name: String,
        originalName: String,
        overview: String,
        posterPath: String,
        backdropPath: String,
        originalLanguage: String,
        genreIds: List<Int>,
        popularity: Double,
        voteAverage: Int,
        voteCount: Int,
        releaseDate: String,
        val video: Boolean,
        val adult: Boolean
    ) : MediaItem(
        id,
        name,
        originalName,
        overview,
        posterPath,
        backdropPath,
        originalLanguage,
        genreIds,
        popularity,
        voteAverage,
        voteCount,
        releaseDate
    )

    class TvShow(
        id: Long,
        name: String,
        originalName: String,
        overview: String,
        posterPath: String,
        backdropPath: String,
        originalLanguage: String,
        genreIds: List<Int>,
        popularity: Double,
        voteAverage: Int,
        voteCount: Int,
        releaseDate: String,
        val originCountry: List<String>
    ) : MediaItem(
        id,
        name,
        originalName,
        overview,
        posterPath,
        backdropPath,
        originalLanguage,
        genreIds,
        popularity,
        voteAverage,
        voteCount,
        releaseDate
    )
}
