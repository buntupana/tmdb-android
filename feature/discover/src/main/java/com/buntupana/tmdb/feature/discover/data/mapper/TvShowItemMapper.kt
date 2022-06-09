package com.buntupana.tmdb.feature.discover.data.mapper

import com.buntupana.tmdb.core.data.DateUtil
import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.feature.discover.data.raw.TvShowRaw
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

fun TvShowRaw.toModel(): MediaItem {

    val releaseLocalDate = LocalDate.parse(firstAirDate)

    val formatter = DateTimeFormatter.ofPattern(DateUtil.dateFormat)
    val releaseDate = releaseLocalDate.format(formatter)

    return MediaItem.TvShow(
        id,
        name,
        originalName,
        overview,
        CoreApi.BASE_URL_IMAGE + posterPath.orEmpty(),
        CoreApi.BASE_URL_IMAGE + backdropPath.orEmpty(),
        originalLanguage,
        genreIds,
        popularity,
        (voteAverage * 10).toInt(),
        voteCount,
        releaseDate,
        originCountry
    )
}