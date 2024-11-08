package com.buntupana.tmdb.feature.detail.presentation.seasons

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SeasonsDetailNavArgs(
    val tvShowId: Long,
    val tvShowName: String,
    val releaseYear: String?,
    val posterUrl: String?,
    val backgroundColor: Int?
) : Parcelable
