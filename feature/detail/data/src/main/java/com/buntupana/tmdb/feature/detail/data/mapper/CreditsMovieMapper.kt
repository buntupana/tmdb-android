package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.raw.CreditsMovieRaw
import com.buntupana.tmdb.feature.detail.domain.model.Credits
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.panabuntu.tmdb.core.common.api.CoreApi
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank

fun CreditsMovieRaw.toModel(): Credits {

    val castList = cast.map {
        val profileUrl =
            it.profilePath.ifNotNullOrBlank { CoreApi.BASE_URL_PROFILE + it.profilePath }
        Person.Cast.Movie(
            id = it.id,
            name = it.name,
            gender = com.panabuntu.tmdb.core.common.mapper.getGender(it.gender),
            profileUrl = profileUrl,
            character = it.character
        )
    }

    val crewList = crew.map {
        val profileUrl =
            it.profilePath.ifNotNullOrBlank { CoreApi.BASE_URL_PROFILE + it.profilePath }
        Person.Crew.Movie(
            id = it.id,
            name = it.name,
            gender = com.panabuntu.tmdb.core.common.mapper.getGender(it.gender),
            profileUrl = profileUrl,
            department = it.department,
            job = it.job
        )
    }

    return Credits(castList, crewList)
}