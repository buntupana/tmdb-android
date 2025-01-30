package com.panabuntu.tmdb.core.common.model

import com.panabuntu.tmdb.core.common.entity.MediaType


sealed class MediaItem(
    open val id: Long,
    open val name: String,
    open val posterUrl: String?,
    open val backdropUrl: String?,
    open val popularity: Float?,
    open val overview: String?,
    open val releaseDate: String,
    open val mediaType: MediaType,
    open val voteAverage: Int?
) {

    data class Movie(
        override val id: Long,
        override val name: String,
        val originalName: String,
        override val overview: String,
        override val posterUrl: String?,
        override val backdropUrl: String?,
        val originalLanguage: String?,
        val genreIds: List<Int>?,
        override val popularity: Float,
        override val voteAverage: Int?,
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
        releaseDate = releaseDate,
        mediaType = MediaType.MOVIE,
        voteAverage =voteAverage
    )

    data class TvShow(
        override val id: Long,
        override val name: String,
        val originalName: String,
        override val overview: String,
        override val posterUrl: String?,
        override val backdropUrl: String?,
        val originalLanguage: String?,
        val genreIds: List<Int>?,
        override val popularity: Float,
        override val voteAverage: Int?,
        val voteCount: Int,
        override val releaseDate: String,
        val originCountry: List<String>
    ) : MediaItem(
        id = id,
        name = name,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        popularity = popularity,
        overview = overview,
        releaseDate = releaseDate,
        mediaType = MediaType.TV_SHOW,
        voteAverage =voteAverage
    )
}
