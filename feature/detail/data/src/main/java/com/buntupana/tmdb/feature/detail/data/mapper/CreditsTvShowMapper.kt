package com.buntupana.tmdb.feature.detail.data.mapper


import com.buntupana.tmdb.core.data.mapper.getGender
import com.buntupana.tmdb.feature.detail.domain.model.CreditsTvShow
import com.buntupana.tmdb.feature.detail.domain.model.Job
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.domain.model.Role
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank

fun com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.CreditsTvShowRaw.toModel(
    baseUrlProfile : String,
): CreditsTvShow {

    val castList = cast.map {
        val profileUrl =
            it.profilePath.ifNotNullOrBlank { baseUrlProfile + it.profilePath }
        Person.Cast.TvShow(
            id = it.id,
            name = it.name,
            gender = getGender(it.gender),
            profileUrl = profileUrl,
            totalEpisodeCount = it.totalEpisodeCount ?: 0,
            roleList = it.roles?.map { roleRaw ->  Role(roleRaw.character, roleRaw.episodeCount ?: 0) }.orEmpty()
        )
    }

    val crewList = crew.map {
        val profileUrl =
            it.profilePath.ifNotNullOrBlank { baseUrlProfile + it.profilePath }
        Person.Crew.TvShow(
            id = it.id,
            name = it.name,
            gender = getGender(it.gender),
            profileUrl = profileUrl,
            department = it.department,
            totalEpisodeCount = it.totalEpisodeCount,
            jobList = it.jobs?.map { jobRaw -> Job(jobRaw.job, jobRaw.episodeCount?: 0) }.orEmpty()
        )
    }

    return CreditsTvShow(castList, crewList)
}