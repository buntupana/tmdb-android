package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.data.mapper.getMediaType
import com.buntupana.tmdb.core.presentation.util.ifNull
import com.buntupana.tmdb.feature.detail.data.raw.FilmographyRaw
import com.buntupana.tmdb.feature.detail.domain.model.CreditPersonItem
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeParseException

fun FilmographyRaw.toModel(): List<CreditPersonItem> {

    val castItemList = cast?.map {

        val posterUrl =
            if (it.posterPath.isNullOrBlank()) it.posterPath else CoreApi.BASE_URL_POSTER + it.posterPath
        val backdropUrl =
            if (it.backdropPath.isNullOrBlank()) it.backdropPath else CoreApi.BASE_URL_POSTER + it.backdropPath

        val releaseDateLocal = try {
            LocalDate.parse(it.releaseDate.orEmpty())
        } catch (exc: DateTimeParseException) {
            null
        }

        CreditPersonItem(
            it.id,
            it.title.ifNull { it.name.orEmpty() },
            getMediaType(it.mediaType),
            "Acting",
            it.character.orEmpty(),
            posterUrl.orEmpty(),
            backdropUrl.orEmpty(),
            it.popularity,
            (it.voteAverage * 100).toInt(),
            it.voteCount,
            releaseDateLocal
        )
    }.orEmpty()

    val crewItemList = crew?.map {

        val posterUrl =
            if (it.posterPath.isNullOrBlank()) it.posterPath else CoreApi.BASE_URL_POSTER + it.posterPath
        val backdropUrl =
            if (it.posterPath.isNullOrBlank()) it.backdropPath else CoreApi.BASE_URL_POSTER + it.backdropPath

        val releaseDateLocal = try {
            LocalDate.parse(it.releaseDate.orEmpty())
        } catch (exc: DateTimeParseException) {
            null
        }

        CreditPersonItem(
            it.id,
            it.title.ifNull { it.name.orEmpty() },
            getMediaType(it.mediaType),
            it.department,
            it.job,
            posterUrl.orEmpty(),
            backdropUrl.orEmpty(),
            it.popularity,
            (it.voteAverage * 100).toInt(),
            it.voteCount,
            releaseDateLocal
        )
    }.orEmpty()

    return castItemList + crewItemList
}