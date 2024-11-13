package com.buntupana.tmdb.feature.detail.presentation.episodes

import android.os.Parcelable
import com.buntupana.tmdb.core.presentation.navigation.Routes
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class EpisodesDetailNav(
    val tvShowId: Long,
    val seasonName: String,
    val seasonNumber: Int,
    val posterUrl: String?,
    val backgroundColor: Int?,
    val releaseYear: String?
) : Parcelable, Routes
