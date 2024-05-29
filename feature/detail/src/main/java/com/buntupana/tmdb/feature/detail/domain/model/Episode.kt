package com.buntupana.tmdb.feature.detail.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Episode(
    val id: Long,
    val showId: Long?,
    val name: String?,
    val airDate: LocalDate?,
    val episodeNumber: Int?,
    val overview: String?,
    val runtime: Int?,
    val seasonNumber: Int?,
    val stillUrl: String?,
    val voteAverage: Double?,
    val voteCount: Int?
) : Parcelable
