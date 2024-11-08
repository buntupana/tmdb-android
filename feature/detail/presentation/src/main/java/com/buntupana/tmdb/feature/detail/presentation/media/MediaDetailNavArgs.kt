package com.buntupana.tmdb.feature.detail.presentation.media

import android.os.Parcelable
import com.buntupana.tmdb.core.domain.entity.MediaType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MediaDetailNavArgs(
    val mediaId: Long,
    val mediaType: MediaType,
    val backgroundColor: Int?
) : Parcelable
