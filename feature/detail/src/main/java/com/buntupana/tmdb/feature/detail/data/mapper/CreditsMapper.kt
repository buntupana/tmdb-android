package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.core.data.api.CoreApi
import com.buntupana.tmdb.feature.detail.data.raw.CreditsRaw
import com.buntupana.tmdb.feature.detail.domain.model.CastPersonItem
import com.buntupana.tmdb.feature.detail.domain.model.Credits
import com.buntupana.tmdb.feature.detail.domain.model.CrewPersonItem

fun CreditsRaw.toModel(): Credits {

    val castList = cast.map {
        CastPersonItem(
            it.id,
            it.name,
            CoreApi.BASE_URL_PROFILE + it.profilePath,
            it.character
        )
    }

    val crewList = crew.map {
        CrewPersonItem(
            it.id,
            it.name,
            CoreApi.BASE_URL_PROFILE + it.profilePath,
            it.job
        )
    }

    return Credits(castList, crewList)
}