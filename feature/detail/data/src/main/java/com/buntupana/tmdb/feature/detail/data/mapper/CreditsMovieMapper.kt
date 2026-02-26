package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.mapper.getGender
import com.buntupana.tmdb.feature.detail.domain.model.CreditsMovie
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank

fun com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.CreditsMovieRaw.toModel(
    baseUrlProfile : String,
): CreditsMovie {

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

    return CreditsMovie(castList, crewList)
}