package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.mapper.getMediaType
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.FilmographyRaw
import com.buntupana.tmdb.feature.detail.domain.model.CreditPersonItem
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import com.panabuntu.tmdb.core.common.util.ifNull
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun FilmographyRaw.toModel(
    baseUrlPoster : String,
    baseUrlBackdrop : String
): List<CreditPersonItem> {

    val castItemList = cast?.map {

        val posterUrl =
            it.posterPath.ifNotNullOrBlank { baseUrlPoster+ it.posterPath.orEmpty() }
        val backdropUrl =
            it.backdropPath.ifNotNullOrBlank { baseUrlBackdrop + it.backdropPath.orEmpty() }

        val releaseDateLocal = try {
            LocalDate.parse(it.releaseDate.ifNull { it.firstAirDate.orEmpty() })
        } catch (exc: DateTimeParseException) {
            null
        }

        when (getMediaType(it.mediaType)) {
            MediaType.MOVIE -> {
                CreditPersonItem.Movie(
                    id = it.id,
                    title = it.title.ifNull { it.name.orEmpty() },
                    department = "Acting",
                    role = it.character.orEmpty(),
                    posterUrl = posterUrl,
                    backdropUrl = backdropUrl,
                    popularity =it.popularity ?: 0f,
                    userScore = ((it.voteAverage ?: 0f) * 100).toInt(),
                    voteCount = it.voteCount ?: 0,
                    releaseDate = releaseDateLocal,
                    castOrder = it.order ?: 999
                )
            }

            MediaType.TV_SHOW -> {
                CreditPersonItem.TvShow(
                    id = it.id,
                    title = it.title.ifNull { it.name.orEmpty() },
                    department = "Acting",
                    role = it.character.orEmpty(),
                    posterUrl = posterUrl,
                    backdropUrl = backdropUrl,
                    popularity = it.popularity ?: 0f,
                    userScore = ((it.voteAverage ?: 0f) * 100).toInt(),
                    voteCount = it.voteCount ?: 0,
                    releaseDate = releaseDateLocal,
                    episodeCount = it.episodeCount ?: 0
                )
            }
        }
    }.orEmpty()

    val crewItemList = crew?.map {

        val posterUrl =
            it.posterPath.ifNotNullOrBlank { baseUrlPoster + it.posterPath.orEmpty() }
        val backdropUrl =
            it.backdropPath.ifNotNullOrBlank { baseUrlBackdrop + it.backdropPath.orEmpty() }

        val releaseDateLocal = try {
            LocalDate.parse(it.releaseDate.ifNull { it.firstAirDate.orEmpty() })
        } catch (exc: DateTimeParseException) {
            null
        }

        when (getMediaType(it.mediaType)) {
            MediaType.MOVIE -> {
                CreditPersonItem.Movie(
                    id = it.id,
                    title = it.title.ifNull { it.name.orEmpty() },
                    department = it.department,
                    role = it.job.orEmpty(),
                    posterUrl = posterUrl,
                    backdropUrl = backdropUrl,
                    popularity = it.popularity,
                    userScore = (it.voteAverage * 100).toInt(),
                    voteCount = it.voteCount ?: 0,
                    releaseDate = releaseDateLocal,
                    castOrder = 0
                )
            }

            MediaType.TV_SHOW -> {
                CreditPersonItem.TvShow(
                    id = it.id,
                    title = it.title.ifNull { it.name.orEmpty() },
                    department = it.department,
                    role = it.job.orEmpty(),
                    posterUrl = posterUrl,
                    backdropUrl = backdropUrl,
                    popularity = it.popularity,
                    userScore = (it.voteAverage * 100).toInt(),
                    voteCount = it.voteCount ?: 0,
                    releaseDate = releaseDateLocal,
                    episodeCount = it.episodeCount ?: 0
                )
            }
        }
    }.orEmpty()

    return castItemList + crewItemList
}