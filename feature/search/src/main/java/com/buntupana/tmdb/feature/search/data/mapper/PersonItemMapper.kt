package com.buntupana.tmdb.feature.search.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.domain.model.MediaItem
import com.buntupana.tmdb.feature.search.data.raw.PersonRaw

fun PersonRaw.toModel(): MediaItem.Person {
    return MediaItem.Person(
        id,
        name,
        CoreApi.BASE_URL_PROFILE + profilePath.orEmpty(),
        popularity,
        adult,
        gender,
        knownForDepartment.orEmpty(),
        knownFor?.map { it.title }.orEmpty()
    )
}