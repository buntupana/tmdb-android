package com.buntupana.tmdb.feature.discover.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.feature.discover.data.raw.MovieItemRaw
import com.buntupana.tmdb.feature.discover.domain.model.MovieItem
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

fun MovieItemRaw.toModel(): MovieItem {

    val releaseLocalDate = LocalDate.parse("2018-12-31")

    val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
    val releaseDate = releaseLocalDate.format(formatter)

    return MovieItem(
        id,
        title,
        originalTitle,
        overview,
        CoreApi.BASE_URL_IMAGE + posterPath.orEmpty(),
        CoreApi.BASE_URL_IMAGE + backdropPath.orEmpty(),
        originalLanguage,
        genreIds,
        popularity,
        (voteAverage * 10).toInt(),
        voteCount,
        releaseDate,
        video,
        adult
    )
}