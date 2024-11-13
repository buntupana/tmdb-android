package com.buntupana.tmdb.feature.detail.data.mapper


import com.buntupana.tmdb.feature.detail.data.raw.CreditsTvShowRaw
import com.buntupana.tmdb.feature.detail.domain.model.CreditsTvShow
import com.buntupana.tmdb.feature.detail.domain.model.Job
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.domain.model.Role
import com.panabuntu.tmdb.core.common.api.CoreApi
import com.panabuntu.tmdb.core.common.ifNotNullOrBlank

fun CreditsTvShowRaw.toModel(): CreditsTvShow {

    val castList = cast.map {
        val profileUrl =
            it.profilePath.ifNotNullOrBlank { CoreApi.BASE_URL_PROFILE + it.profilePath }
        Person.Cast.TvShow(
            id = it.id,
            name = it.name,
            gender = com.panabuntu.tmdb.core.common.mapper.getGender(it.gender),
            profileUrl = profileUrl,
            totalEpisodeCount = it.totalEpisodeCount ?: 0,
            roleList = it.roles?.map { roleRaw ->  Role(roleRaw.character, roleRaw.episodeCount ?: 0) }.orEmpty()
        )
    }

    val crewList = crew.map {
        val profileUrl =
            it.profilePath.ifNotNullOrBlank { CoreApi.BASE_URL_PROFILE + it.profilePath }
        Person.Crew.TvShow(
            id = it.id,
            name = it.name,
            gender = com.panabuntu.tmdb.core.common.mapper.getGender(it.gender),
            profileUrl = profileUrl,
            department = it.department,
            totalEpisodeCount = it.totalEpisodeCount,
            jobList = it.jobs.map { jobRaw -> Job(jobRaw.job, jobRaw.episodeCount?: 0) }
        )
    }

    return CreditsTvShow(castList, crewList)
}