package com.buntupana.tmdb.feature.search.data.mapper

import com.buntupana.tmdb.core.data.raw.MediaItemRaw
import com.buntupana.tmdb.feature.search.domain.model.SearchItem
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank

fun List<MediaItemRaw>.toSearchModel(
    baseUrlPoster : String
): List<SearchItem> {
    val result = mutableListOf<SearchItem>()

    forEach { item ->

        val posterUrl =
            item.posterPath.ifNotNullOrBlank { baseUrlPoster + item.posterPath.orEmpty() }

        when (item.mediaType) {
            "tv" -> {
                result.add(
                    SearchItem.TvShow(
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
                    SearchItem.Movie(
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
                    SearchItem.Person(
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