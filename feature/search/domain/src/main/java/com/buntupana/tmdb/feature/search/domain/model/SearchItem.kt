package com.buntupana.tmdb.feature.search.domain.model

sealed class SearchItem(
    open val id: Long,
    open val name: String,
    open val originalName: String?,
    open val imageUrl: String?,
    open val popularity: Float?,
    var isHighlighted: Boolean = false
) {

    data class Movie(
        override val id: Long,
        override val name: String,
        override val originalName: String?,
        override val imageUrl: String?,
        val originalLanguage: String?,
        override val popularity: Float,
        val voteAverage: Int,
        val voteCount: Int,
    ) : SearchItem(
        id = id,
        name = name,
        originalName = originalName,
        imageUrl = imageUrl,
        popularity = popularity,
    )

    data class TvShow(
        override val id: Long,
        override val name: String,
        override val originalName: String?,
        override val imageUrl: String?,
        override val popularity: Float,
        val voteAverage: Int,
        val voteCount: Int
    ) : SearchItem(
        id = id,
        name = name,
        originalName = originalName,
        imageUrl = imageUrl,
        popularity = popularity
    )

    data class Person(
        override val id: Long,
        override val name: String,
        override val originalName: String?,
        override val imageUrl: String?,
        override val popularity: Float,
    ): SearchItem(
        id = id,
        name = name,
        originalName = originalName,
        imageUrl = imageUrl,
        popularity = popularity
    )
}
