package com.buntupana.tmdb.feature.discover.data.mapper

import com.buntupana.tmdb.core.data.DateUtil
import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.feature.discover.data.raw.MovieItemRaw
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

fun MovieItemRaw.toModel(): MediaItem.Movie {

    val releaseLocalDate = LocalDate.parse(releaseDate)

    val formatter = DateTimeFormatter.ofPattern(DateUtil.dateFormat)
    val releaseDate = releaseLocalDate.format(formatter)

    return MediaItem.Movie(
        id,
        title,
        originalTitle,
        overview,
        CoreApi.BASE_URL_IMAGE_POSTER + posterPath.orEmpty(),
        CoreApi.BASE_URL_IMAGE_POSTER + backdropPath.orEmpty(),
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