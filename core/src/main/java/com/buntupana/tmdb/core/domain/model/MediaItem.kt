package com.buntupana.tmdb.core.domain.model

sealed class MediaItem(
    open val id: Long,
    open val name: String,
    open val posterUrl: String?,
    open val backdropUrl: String?,
    open val popularity: Double,
    open val overview: String,
    open val releaseDate: String
) {

    data class Movie(
        override val id: Long,
        override val name: String,
        val originalName: String,
        override val overview: String,
        override val posterUrl: String?,
        override val backdropUrl: String?,
        val originalLanguage: String,
        val genreIds: List<Int>,
        override val popularity: Double,
        val voteAverage: Int,
        val voteCount: Int,
        override val releaseDate: String,
        val video: Boolean,
        val adult: Boolean
    ) : MediaItem(
        id = id,
        name = name,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        popularity = popularity,
        overview = overview,
        releaseDate = releaseDate
    )

    data class TvShow(
        override val id: Long,
        override val name: String,
        val originalName: String,
        override val overview: String,
        override val posterUrl: String?,
        override val backdropUrl: String?,
        val originalLanguage: String,
        val genreIds: List<Int>,
        override val popularity: Double,
        val voteAverage: Int,
        val voteCount: Int,
        override val releaseDate: String,
        val originCountry: List<String>
    ) : MediaItem(
        id = id,
        name = name,
        posterUrl = posterUrl,
        backdropUrl = Unknown.backdropUrl,
        popularity = popularity,
        overview = overview,
        releaseDate = releaseDate
    )

    data object Unknown : MediaItem(
        id = 0,
        name = "",
        posterUrl = null,
        backdropUrl = null,
        popularity = 0.0,
        overview = "",
        releaseDate = ""
    )
}
