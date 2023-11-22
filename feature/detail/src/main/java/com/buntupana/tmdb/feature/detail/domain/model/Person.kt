package com.buntupana.tmdb.feature.detail.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class Person(
    open val id: Long,
    open val name: String,
    open val profileUrl: String
) : Parcelable {
    data class Cast(
        override val id: Long,
        override val name: String,
        override val profileUrl: String,
        val character: String
    ) : Person(id, name, profileUrl)

    data class Crew(
        override val id: Long,
        override val name: String,
        override val profileUrl: String,
        val department: String,
        val job: String
    ) : Person(id, name, profileUrl)
}
