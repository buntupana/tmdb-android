package com.buntupana.tmdb.feature.lists.data.mapper

import com.buntupana.tmdb.feature.lists.domain.model.ListDetail
import com.panabuntu.tmdb.core.common.util.ifNotNull
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank

fun com.buntupana.tmdb.feature.lists.data.remote_data_source.raw.ListDetailRaw.toModel(
    baseUrlBackdrop: String
): ListDetail {

    val backdropUrl =
        backdropPath.ifNotNullOrBlank { baseUrlBackdrop + backdropPath.orEmpty() }

    return ListDetail(
        id = id,
        name = name,
        description = description,
        itemCount = itemCount,
        averageRating = averageRating.ifNotNull { (it * 10).toInt() },
        backdropUrl = backdropUrl,
        isPublic = public,
        revenue = revenue ?: 0,
        runtime = runtime,
    )
}