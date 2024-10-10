package com.buntupana.tmdb.feature.detail.presentation.episodes

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class EpisodesDetailNavArgs(
    val tvShowId: Long,
    val seasonName: String,
    val seasonNumber: Int,
    val posterUrlEncoded: String?,
    val backgroundColor: Int?,
    val releaseYear: String?
) : Parcelable
