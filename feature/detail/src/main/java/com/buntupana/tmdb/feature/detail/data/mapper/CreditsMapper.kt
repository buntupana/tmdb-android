package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.feature.detail.data.raw.CreditsRaw
import com.buntupana.tmdb.feature.detail.domain.model.Credits
import com.buntupana.tmdb.feature.detail.domain.model.Person

fun CreditsRaw.toModel(): Credits {

    val castList = cast.map {
        Person.Cast(
            id = it.id,
            name = it.name,
            profileUrl = CoreApi.BASE_URL_PROFILE + it.profilePath,
            character = it.character
        )
    }

    val crewList = crew.map {
        Person.Crew(
            id = it.id,
            name = it.name,
            profileUrl = CoreApi.BASE_URL_PROFILE + it.profilePath,
            department = it.department,
            job = it.job
        )
    }

    return Credits(castList, crewList)
}