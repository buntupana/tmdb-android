package com.buntupana.tmdb.feature.lists.data.mapper

import com.buntupana.tmdb.core.data.database.entity.MediaListEntity
import com.buntupana.tmdb.core.data.database.entity.MediaListSimpleEntity
import com.buntupana.tmdb.feature.lists.data.remote_data_source.raw.ListDetailRaw
import com.buntupana.tmdb.feature.lists.data.remote_data_source.raw.ListItemRaw
import com.buntupana.tmdb.feature.lists.domain.model.MediaList
import com.panabuntu.tmdb.core.common.util.ifNotNull
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank
import java.time.LocalDate

fun List<ListItemRaw>.toEntity(): List<MediaListEntity> {

    return map { it.toEntity() }
}

fun ListItemRaw.toEntity(): MediaListEntity {

    return MediaListEntity(
        id = id,
        name = name,
        posterPath = posterPath,
        backdropPath = backdropPath,
        averageRating = averageRating,
        description = description,
        itemCount = numberOfItems,
        isPublic = public == 1,
        revenue = revenue,
        runtime = runtime,
        updatedAt = updatedAt,
        accountObjectId = accountObjectId,
        adult = adult,
        iso31661 = iso_3166_1,
        iso6391 = iso_639_1,
        featured = featured,
        sortBy = sortBy,
        createdAt = createdAt
    )
}

fun MediaListEntity.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String,
): MediaList {

    val updatedAt = try {
        LocalDate.parse(updatedAt)
    } catch (_: Exception) {
        null
    }

    val posterUrl =
        posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    return MediaList(
        id = id,
        name = name,
        backdropUrl = backdropUrl,
        posterUrl = posterUrl,
        averageRating = averageRating.ifNotNull { (it * 10).toInt() },
        description = description,
        itemCount = itemCount,
        isPublic = isPublic,
        revenue = revenue,
        runtime = if (runtime.isNotNullOrBlank()) null else runtime?.toInt(),
        updatedAt = updatedAt
    )
}

fun List<ListItemRaw>.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String,
): List<MediaList> {
    return map { it.toModel(baseUrlPoster, baseUrlBackdrop) }
}

fun ListItemRaw.toModel(
    baseUrlPoster: String,
    baseUrlBackdrop: String,
): MediaList {
    val updatedAt = try {
        LocalDate.parse(updatedAt)
    } catch (_: Exception) {
        null
    }

    val posterUrl =
        posterPath.ifNotNullOrBlank { baseUrlPoster + posterPath.orEmpty() }
    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    return MediaList(
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

fun ListDetailRaw.toEntity(): MediaListSimpleEntity {

    return MediaListSimpleEntity(
        id = id,
        name = name,
        description = description,
        itemCount = itemCount,
        averageRating = averageRating,
        backdropPath = backdropPath,
        isPublic = public,
        revenue = revenue ?: 0,
        runtime = runtime.toString(),
        iso31661 = iso31661,
        iso6391 = iso6391,
        posterPath = posterPath,
    )
}