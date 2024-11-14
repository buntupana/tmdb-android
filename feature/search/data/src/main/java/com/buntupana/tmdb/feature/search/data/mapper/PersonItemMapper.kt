package com.buntupana.tmdb.feature.search.data.mapper

import com.buntupana.tmdb.data.mapper.getGender
import com.buntupana.tmdb.feature.search.data.raw.PersonRaw
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank
import com.panabuntu.tmdb.core.common.isNotNullOrBlank

fun PersonRaw.toModel(
    baseUrlProfile: String
): com.panabuntu.tmdb.core.common.model.PersonItem {

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
        profilePath = profilePath.ifNotNullOrBlank { baseUrlProfile + profilePath.orEmpty() },
        popularity = popularity,
        adult = adult,
        gender = getGender(gender),
        knownForDepartment = knownForDepartment.orEmpty(),
        knownForList = knownForList
    )
}