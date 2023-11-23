package com.buntupana.tmdb.feature.detail.domain.model

import android.os.Parcelable
import com.buntupana.tmdb.core.domain.model.Gender
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class Person(
    open val id: Long,
    open val name: String,
    open val gender: Gender,
    open val profileUrl: String?
) : Parcelable {
    data class Cast(
        override val id: Long,
        override val name: String,
        override val gender: Gender,
        override val profileUrl: String?,
        val character: String
    ) : Person(id = id, name = name, gender = gender, profileUrl = profileUrl)

    data class Crew(
        override val id: Long,
        override val name: String,
        override val gender: Gender,
        override val profileUrl: String?,
        val department: String,
        val job: String
    ) : Person(id = id, name = name, gender = gender, profileUrl = profileUrl)
}
