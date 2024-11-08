package com.buntupana.tmdb.feature.search.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.core.data.mapper.getGender
import com.buntupana.tmdb.core.domain.model.PersonItem
import com.buntupana.tmdb.core.presentation.util.ifNotNullOrBlank
import com.buntupana.tmdb.core.presentation.util.isNotNullOrBlank
import com.buntupana.tmdb.feature.search.data.raw.PersonRaw

fun PersonRaw.toModel(): PersonItem {

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
        profilePath = profilePath.ifNotNullOrBlank { CoreApi.BASE_URL_PROFILE + profilePath.orEmpty() },
        popularity = popularity,
        adult = adult,
        gender = getGender(gender),
        knownForDepartment = knownForDepartment.orEmpty(),
        knownForList = knownForList
    )
}