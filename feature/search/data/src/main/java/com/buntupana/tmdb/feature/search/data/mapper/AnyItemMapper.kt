package com.buntupana.tmdb.feature.search.data.mapper

import com.buntupana.tmdb.data.raw.AnyMediaItemRaw
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank

fun List<AnyMediaItemRaw>.toSearchModel(
    baseUrlPoster : String
): List<com.buntupana.tmdb.feature.search.domain.model.SearchItem> {
    val result = mutableListOf<com.buntupana.tmdb.feature.search.domain.model.SearchItem>()

    forEach { item ->

        val posterUrl =
            item.posterPath.ifNotNullOrBlank { baseUrlPoster + item.posterPath.orEmpty() }

        when (item.mediaType) {
            "tv" -> {
                result.add(
                    com.buntupana.tmdb.feature.search.domain.model.SearchItem.TvShow(
                        id = item.id,
                        name = item.name.orEmpty(),
                        originalName = item.originalName.orEmpty(),
                        imageUrl = posterUrl,
                        popularity = item.popularity ?: 0f,
                        voteAverage = ((item.voteAverage ?: 0f) * 10).toInt(),
                        voteCount = item.voteCount ?: 0,
                    )
                )
            }

            "movie" -> {
                result.add(
                    com.buntupana.tmdb.feature.search.domain.model.SearchItem.Movie(
                        id = item.id,
                        name = item.title.orEmpty(),
                        originalName = item.originalTitle.orEmpty(),
                        imageUrl = posterUrl,
                        originalLanguage = item.originalLanguage,
                        popularity = item.popularity ?: 0f,
                        voteAverage = ((item.voteAverage ?: 0f) * 10).toInt(),
                        voteCount = item.voteCount ?: 0,
                    )
                )
            }

            "person" -> {
                result.add(
                    com.buntupana.tmdb.feature.search.domain.model.SearchItem.Person(
                        id = item.id,
                        name = item.name.orEmpty(),
                        originalName = item.originalName.orEmpty(),
                        imageUrl = posterUrl,
                        popularity = item.popularity ?: 0f,
                    )
                )
            }
            else -> {}
        }
    }
    return result
}