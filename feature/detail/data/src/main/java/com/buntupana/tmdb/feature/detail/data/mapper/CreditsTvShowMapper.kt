package com.buntupana.tmdb.feature.detail.data.mapper


import com.buntupana.tmdb.core.data.mapper.getGender
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.CreditsTvShowRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.MediaCastGuestStarRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.MediaCastTvShowRaw
import com.buntupana.tmdb.feature.detail.data.remote_data_source.raw.MediaCrewTvShowRaw
import com.buntupana.tmdb.feature.detail.domain.model.CreditsTvShow
import com.buntupana.tmdb.feature.detail.domain.model.Job
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.domain.model.Role
import com.panabuntu.tmdb.core.common.util.ifNotNullOrBlank

fun CreditsTvShowRaw.toModel(
    baseUrlProfile: String,
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
            roleList = it.roles?.map { roleRaw ->
                Role(
                    roleRaw.character,
                    roleRaw.episodeCount ?: 0
                )
            }.orEmpty()
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
            jobList = it.jobs?.map { jobRaw -> Job(jobRaw.job, jobRaw.episodeCount ?: 0) }.orEmpty()
        )
    }

    return CreditsTvShow(castList, crewList)
}

@JvmName("toModelTvShowCast")
fun List<MediaCastTvShowRaw>.toModel(
    baseUrlProfile: String
): List<Person.Cast.TvShow> {
    return map {
        val profileUrl =
            it.profilePath.ifNotNullOrBlank { baseUrlProfile + it.profilePath }
        Person.Cast.TvShow(
            id = it.id,
            name = it.name,
            gender = getGender(it.gender),
            profileUrl = profileUrl,
            totalEpisodeCount = it.totalEpisodeCount ?: 0,
            roleList = it.roles?.map { roleRaw ->
                Role(
                    roleRaw.character,
                    roleRaw.episodeCount ?: 0
                )
            }.orEmpty()
        )
    }
}

@JvmName("toModelEpisodeCast")
fun List<MediaCastGuestStarRaw>.toModel(
    baseUrlProfile: String
): List<Person.Cast.TvShow> {
    return map {
        val profileUrl =
            it.profilePath.ifNotNullOrBlank { baseUrlProfile + it.profilePath }
        Person.Cast.TvShow(
            id = it.id,
            name = it.name,
            gender = getGender(it.gender),
            profileUrl = profileUrl,
            totalEpisodeCount = 0,
            roleList = listOf(Role(character = it.character, episodeCount = 0))
        )
    }
}

@JvmName("toModelTvShowCrew")
fun List<MediaCrewTvShowRaw>.toModel(
    baseUrlProfile: String
): List<Person.Crew.TvShow> {
    return map {
        val profileUrl =
            it.profilePath.ifNotNullOrBlank { baseUrlProfile + it.profilePath }
        Person.Crew.TvShow(
            id = it.id,
            name = it.name,
            gender = getGender(it.gender),
            profileUrl = profileUrl,
            department = it.department,
            totalEpisodeCount = it.totalEpisodeCount,
            jobList = it.jobs?.map { jobRaw -> Job(jobRaw.job, jobRaw.episodeCount ?: 0) }.orEmpty()
        )
    }
}