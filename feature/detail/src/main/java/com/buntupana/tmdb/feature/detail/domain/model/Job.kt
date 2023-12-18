package com.buntupana.tmdb.feature.detail.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Job(
    val job: String,
    val episodeCount: Int
) : Parcelable