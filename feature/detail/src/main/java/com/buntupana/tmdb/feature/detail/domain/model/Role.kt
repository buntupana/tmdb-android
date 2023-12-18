package com.buntupana.tmdb.feature.detail.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Role(
    val character: String,
    val episodeCount: Int
) : Parcelable