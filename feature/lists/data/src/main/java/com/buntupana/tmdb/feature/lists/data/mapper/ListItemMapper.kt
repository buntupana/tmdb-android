package com.buntupana.tmdb.feature.lists.data.mapper

import com.buntupana.tmdb.feature.lists.data.remote_data_source.raw.ListItemRaw
import com.buntupana.tmdb.feature.lists.domain.model.ListItem
import com.panabuntu.tmdb.core.common.util.ifNotNull
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun List<ListItemRaw>.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String,
): List<ListItem> {

    return map { it.toModel(baseUrlPoster, baseUrlBackdrop) }
}

fun ListItemRaw.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String,
): ListItem {
    val updatedAt = try {
        LocalDate.parse(updatedAt)
    } catch (exc: DateTimeParseException) {
        null
    }

    val posterUrl =
        posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    return ListItem(
        id = id,
        name = name,
        backdropUrl = backdropUrl,
        posterUrl = posterUrl,
        averageRating = averageRating.ifNotNull { (it * 10).toInt() },
        description = description,
        itemCount = numberOfItems,
        isPublic = public == 1,
        revenue = revenue,
        runtime = if (runtime.isNotNullOrBlank()) null else runtime?.toInt(),
        updatedAt = updatedAt
    )
}