package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.mapper.getGender
import com.buntupana.tmdb.feature.detail.data.raw.CreditsMovieRaw
import com.buntupana.tmdb.feature.detail.domain.model.Credits
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank

fun CreditsMovieRaw.toModel(
    baseUrlProfile : String,
): Credits {

    val castList = cast.map {
        val profileUrl =
            it.profilePath.ifNotNullOrBlank { baseUrlProfile + it.profilePath }
        Person.Cast.Movie(
            id = it.id,
            name = it.name,
            gender = getGender(it.gender),
            profileUrl = profileUrl,
            character = it.character
        )
    }

    val crewList = crew.map {
        val profileUrl =
            it.profilePath.ifNotNullOrBlank { baseUrlProfile + it.profilePath }
        Person.Crew.Movie(
            id = it.id,
            name = it.name,
            gender = getGender(it.gender),
            profileUrl = profileUrl,
            department = it.department,
            job = it.job
        )
    }

    return Credits(castList, crewList)
}