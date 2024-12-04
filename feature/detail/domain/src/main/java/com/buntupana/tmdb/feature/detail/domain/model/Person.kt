package com.buntupana.tmdb.feature.detail.domain.model

sealed class Person(
    open val id: Long,
    open val name: String,
    open val gender: com.panabuntu.tmdb.core.common.model.Gender,
    open val profileUrl: String?
) {

    sealed class Cast(
        override val id: Long,
        override val name: String,
        override val gender: com.panabuntu.tmdb.core.common.model.Gender,
        override val profileUrl: String?
    ) : Person(
        id = id,
        name = name,
        gender = gender,
        profileUrl = profileUrl
    ) {

        data class Movie(
            override val id: Long,
            override val name: String,
            override val gender: com.panabuntu.tmdb.core.common.model.Gender,
            override val profileUrl: String?,
            val character: String
        ) : Cast(
            id = id,
            name = name,
            gender = gender,
            profileUrl = profileUrl
        )

        data class TvShow(
            override val id: Long,
            override val name: String,
            override val gender: com.panabuntu.tmdb.core.common.model.Gender,
            override val profileUrl: String?,
            val totalEpisodeCount: Int,
            val roleList: List<Role>
        ) : Cast(
            id = id,
            name = name,
            gender = gender,
            profileUrl = profileUrl
        )
    }

    sealed class Crew(
        override val id: Long,
        override val name: String,
        override val gender: com.panabuntu.tmdb.core.common.model.Gender,
        override val profileUrl: String?,
        open val department: String
    ) : Person(
        id = id,
        name = name,
        gender = gender,
        profileUrl = profileUrl
    ) {

        data class Movie(
            override val id: Long,
            override val name: String,
            override val gender: com.panabuntu.tmdb.core.common.model.Gender,
            override val profileUrl: String?,
            override val department: String,
            val job: String
        ) : Crew(
            id = id,
            name = name,
            gender = gender,
            profileUrl = profileUrl,
            department = department
        )

        data class TvShow(
            override val id: Long,
            override val name: String,
            override val gender: com.panabuntu.tmdb.core.common.model.Gender,
            override val profileUrl: String?,
            override val department: String,
            val totalEpisodeCount: Int?,
            val jobList: List<Job>
        ) : Crew(
            id = id,
            name = name,
            gender = gender,
            profileUrl = profileUrl,
            department = department
        )
    }
}
