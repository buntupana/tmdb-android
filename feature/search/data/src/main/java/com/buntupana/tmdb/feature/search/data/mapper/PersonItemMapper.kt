package com.buntupana.tmdb.feature.search.data.mapper

import com.buntupana.tmdb.core.data.mapper.getGender
import com.buntupana.tmdb.feature.search.data.raw.PersonRaw
import com.panabuntu.tmdb.core.common.model.PersonItem
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank

fun List<PersonRaw>.toModel(
    baseUrlProfile: String
): List<PersonItem> {
    return map { it.toModel(baseUrlProfile) }
}

fun PersonRaw.toModel(
    baseUrlProfile: String
): PersonItem {

    val knownForList = knownFor?.flatMap { knownFor ->
        when {
            knownFor.title.isNotNullOrBlank() -> listOf(knownFor.title.orEmpty())
            knownFor.name.isNotNullOrBlank() -> listOf(knownFor.name.orEmpty())
            else -> listOf()
        }
    }.orEmpty()

    return PersonItem(
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