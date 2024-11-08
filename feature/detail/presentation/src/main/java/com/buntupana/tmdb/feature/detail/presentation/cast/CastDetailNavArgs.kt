package com.buntupana.tmdb.feature.detail.presentation.cast

import android.os.Parcelable
import com.buntupana.tmdb.core.domain.entity.MediaType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CastDetailNavArgs(
    val mediaId: Long,
    val mediaType: MediaType,
    val mediaTitle: String,
    val posterUrl: String?,
    val backgroundColor: Int?,
    val releaseYear: String?
) : Parcelable
