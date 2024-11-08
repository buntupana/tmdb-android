package com.buntupana.tmdb.feature.detail.presentation.person

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class PersonDetailNavArgs(
    val personId: Long
) : Parcelable
