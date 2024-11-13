package com.buntupana.tmdb.feature.detail.presentation.person

import android.os.Parcelable
import com.buntupana.tmdb.core.presentation.navigation.Routes
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class PersonDetailNav(
    val personId: Long
) : Parcelable, Routes
