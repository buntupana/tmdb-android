package com.buntupana.tmdb.feature.search.data.mapper

import com.buntupana.tmdb.feature.search.data.raw.PersonRaw
import com.panabuntu.tmdb.core.common.api.CoreApi
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank
import com.panabuntu.tmdb.core.common.isNotNullOrBlank

fun PersonRaw.toModel(): com.panabuntu.tmdb.core.common.model.PersonItem {

    val knownForList = knownFor?.flatMap { knownFor ->
        when {
            knownFor.title.isNotNullOrBlank() -> listOf(knownFor.title.orEmpty())
            knownFor.name.isNotNullOrBlank() -> listOf(knownFor.name.orEmpty())
            else -> listOf()
        }
    }.orEmpty()

    return com.panabuntu.tmdb.core.common.model.PersonItem(
        id = id,
        name = name,
        profilePath = profilePath.ifNotNullOrBlank { CoreApi.BASE_URL_PROFILE + profilePath.orEmpty() },
        popularity = popularity,
        adult = adult,
        gender = com.panabuntu.tmdb.core.common.mapper.getGender(gender),
        knownForDepartment = knownForDepartment.orEmpty(),
        knownForList = knownForList
    )
}