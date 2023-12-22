package com.buntupana.tmdb.feature.detail.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Season(
    val id: Long,
    val name: String,
    val airDate: LocalDate?,
    val episodeCount: Int?,
    val overview: String?,
    val posterUrl: String?,
    val seasonNumber: Int?,
    val voteAverage: Float
) : Parcelable
